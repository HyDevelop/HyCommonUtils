package cc.moecraft.utils.hyexp;

import cc.moecraft.utils.MathUtils;
import cc.moecraft.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static cc.moecraft.utils.MathUtils.getRandom;
import static cc.moecraft.utils.MathUtils.round;
import static cc.moecraft.utils.StringUtils.repeat;
import static cc.moecraft.utils.hyexp.HyExpComplexUtils.resolveComplexJS;
import static cc.moecraft.utils.hyexp.HyExpComplexUtils.resolveComplexSafe;
import static cc.moecraft.utils.hyexp.HyExpPatterns.patterns;
import static cc.moecraft.utils.hyexp.HyExpUtils.process;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * HyExpression的格式:
 *
 * 基础格式:
 * - 随机整数:            %ri{最小,最大}
 * - 随机double:          %rd{最小,最大,小数点后几位}
 * - 随机字符串:          %rs{可能的字符串1,可能的字符串2...}
 * - 指定几率随机字符串:  %rp{可能的字符串1,几率1,可能的字符串2,几率2...}
 * - 重复字符串:          %ac{字符串,数量}
 * - 处理复杂前转义字符:  %cp{字符}
 * - 处理复杂后转义字符:  %ca{字符}
 *
 * 复杂格式:
 * - 简单计算:            %cal{算式}
 * - 定义变量:            %var{变量名,值} // 可以覆盖
 * - 使用变量:            %val{变量名}
 *
 * 前缀设置:
 * - 使用 %pref{设置标签名, ...} 即可设置.
 * - 这些设置默认全都是False, 如果有标签就是True了.
 *
 * 设置标签:
 * - %pref{rc}: 是否解析指令字符 (比如把\\n解析成NewLine).
 * - %pref{ps}: 是否保留多个空格.
 * - %pref{no}: 不处理这一句 (会把这个pref删掉).
 *
 * 基础例子:
 * - "今天%rs{很不错,很差,很もえ,很适合写一天代码,很适合玩TH14 EX,很适合删库跑路}"
 * - "您的暑假余额不足, 还剩%ri{-100,-1}天, 请充值"
 * - "抽到了: %rp{SSR卡,3,SR卡,17,R卡,40,N卡,40}"
 *
 * 复杂例子
 * - "%var{a,100}%var{b,50}%cal{(a+b)*b}"  // 会返回7500
 * - "%var{a,%ri{1,100}}%var{b,%rd{0,1}},%cal{(a+b)*(a+b)*%rd{1,2,4}}"
 *
 * @author Hykilpikonna
 */
@NoArgsConstructor @AllArgsConstructor
public class HyExpression
{
    /**
     * 解析一条HyExp
     *
     * @param input 表达式字符串
     * @param safeMode 是否安全模式, 如果是本地用推荐false, 如果是公开推荐true.
     * @return 解析后的结果
     */
    public static String resolve(String input, boolean safeMode)
    {
        // Detects if it is empty.
        if (input == null || input.isEmpty()) return "";

        Matcher matcher;

        boolean preserveSpace = false;
        boolean resolveCommands = false;

        // %pref{} (Preferences)
        matcher = patterns.find.pref.matcher(input);
        while (matcher.find())
        {
            String[] tags = matcher.group().split(",");

            for (String tag : tags)
            {
                switch (tag)
                {
                    case "ps": preserveSpace = true; continue;
                    case "rc": resolveCommands = true; continue;
                    case "no": return patterns.replace.pref.matcher(input).replaceAll("");
                    default: return "Error: PREF标签" + tag + "未识别";
                }
            }
        }

        // Actually implementing the settings.
        if (!preserveSpace) input = StringUtils.trimSpaces(input);
        if (resolveCommands) input = StringUtils.escape(input);

        // %ac{} (Append chars)
        process(patterns.find.ac, input, tags -> repeat(tags[0], parseInt(tags[1])));

        // %ri{} (Random int)
        process(patterns.find.ri, input, tags -> getRandom(parseFloat(tags[0]), parseFloat(tags[1])));

        // %rd{} (Random double)
        process(patterns.find.rd, input, tags -> round(getRandom(parseDouble(tags[0]),parseDouble(tags[1])), tags.length == 3 ? parseInt(tags[2]) : 2));

        // %rs{} (Random string)
        process(patterns.find.rs, input, tags -> tags[getRandom(0, tags.length - 1)]);

        // rp
        matcher = patterns.find.rp.matcher(input);
        while (matcher.find())
        {
            String[] rpTag = matcher.group().split(",");
            Map<Double, String> texts = new LinkedHashMap<>();

            double max = 0;
            for (int i = 0; i < rpTag.length; i += 2)
            {
                double chance = Double.valueOf(rpTag[i + 1]);
                if (chance <= 0) continue;

                max += chance;
                texts.put(max, rpTag[i]);
            }

            String text = "ERROR";
            double random = getRandom(0, max);
            for (Map.Entry<Double, String> textEntry : texts.entrySet())
            {
                text = textEntry.getValue();
                if (textEntry.getKey() > random) break;
            }

            input = patterns.replace.rp.matcher(input).replaceFirst(text);
        }

        return safeMode ? resolveComplexSafe(input) : resolveComplexJS(input);
    }
}
