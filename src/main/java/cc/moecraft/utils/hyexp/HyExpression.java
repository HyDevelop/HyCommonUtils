package cc.moecraft.utils.hyexp;

import cc.moecraft.utils.MathUtils;
import cc.moecraft.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static cc.moecraft.utils.hyexp.HyExpComplexUtils.resolveComplexJS;
import static cc.moecraft.utils.hyexp.HyExpComplexUtils.resolveComplexSafe;
import static cc.moecraft.utils.hyexp.HyExpPatterns.patterns;

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
     * @param raw 表达式字符串
     * @param safeMode 是否安全模式, 如果是本地用推荐false, 如果是公开推荐true.
     * @return 解析后的结果
     */
    public static String resolve(String raw, boolean safeMode)
    {
        // Detects if it is empty.
        if (raw == null || raw.isEmpty()) return "";

        Matcher matcher;

        boolean preserveSpace = false;
        boolean resolveCommands = false;

        // %pref{} (Preferences)
        matcher = patterns.find.pref.matcher(raw);
        while (matcher.find())
        {
            String[] tags = matcher.group().toLowerCase().replace(" ", "").replace("\n", "").split(",");

            for (String tag : tags)
            {
                switch (tag)
                {
                    case "ps": preserveSpace = true; continue;
                    case "rc": resolveCommands = true; continue;
                    case "no": return patterns.replace.pref.matcher(raw).replaceAll("");
                    default: return "Error: PREF标签" + tag + "未识别";
                }
            }
        }

        // Actually implementing the settings.
        if (!preserveSpace) raw = StringUtils.trimSpaces(raw);
        if (resolveCommands) raw = StringUtils.escape(raw);

        // %ac{} (Append chars)
        matcher = patterns.find.ac.matcher(raw);
        while (matcher.find())
        {
            String[] tags = matcher.group().split(",");

            String text = tags[0];
            int times = Integer.parseInt(tags[1]);

            raw = patterns.replace.ac.matcher(raw).replaceFirst()
        }

        // ri
        matcher = patterns.find.ri.matcher(raw);
        while (matcher.find())
        {
            String[] riTag = matcher.group().split(",");

            int num0 = Math.round(Float.parseFloat(riTag[0]));
            int num1 = Math.round(Float.parseFloat(riTag[1]));

            raw = patterns.replace.ri.matcher(raw).replaceFirst(String.valueOf(MathUtils.getRandom(Math.min(num0, num1), Math.max(num0, num1))));
        }

        // rd
        matcher = patterns.find.rd.matcher(raw);
        while (matcher.find())
        {
            String[] rdTag = matcher.group().split(",");

            double num0 = Double.parseDouble(rdTag[0]);
            double num1 = Double.parseDouble(rdTag[1]);
            int round = rdTag.length == 3 ? Integer.parseInt(rdTag[2]) : 2;

            raw = patterns.replace.rd.matcher(raw).replaceFirst(String.valueOf(MathUtils.round(MathUtils.getRandom(Math.min(num0, num1), Math.max(num0, num1)), round)));
        }

        // rs
        matcher = patterns.find.rs.matcher(raw);
        while (matcher.find())
        {
            String[] rsTag = matcher.group().split(",");
            raw = patterns.replace.rs.matcher(raw).replaceFirst(rsTag[MathUtils.getRandom(0, rsTag.length - 1)]);
        }

        // rp
        matcher = patterns.find.rp.matcher(raw);
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
            double random = MathUtils.getRandom(0, max);
            for (Map.Entry<Double, String> textEntry : texts.entrySet())
            {
                text = textEntry.getValue();
                if (textEntry.getKey() > random) break;
            }

            raw = patterns.replace.rp.matcher(raw).replaceFirst(text);
        }

        return safeMode ? resolveComplexSafe(raw) : resolveComplexJS(raw);
    }
}
