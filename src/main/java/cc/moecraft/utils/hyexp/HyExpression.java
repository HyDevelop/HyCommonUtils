package cc.moecraft.utils.hyexp;

import cc.moecraft.utils.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import static cc.moecraft.utils.MathUtils.*;
import static cc.moecraft.utils.StringUtils.*;
import static cc.moecraft.utils.hyexp.HyExpComplexUtils.*;
import static cc.moecraft.utils.hyexp.HyExpPattern.*;
import static cc.moecraft.utils.hyexp.HyExpUtils.*;

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
 * - 重复字符串:          %as{字符串,数量,分隔字符串}
 * - 处理复杂前转义字符:  %cb{字符}
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
 * - %pref{ps}: 是否保留多个空格.
 * - %pref{rc}: 是否解析指令字符 (比如把\\n解析成NewLine).
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

        // 0: PS (Preserve Spaces)
        // 1: RC (Resolve Commands)
        // 2: NO (Don't Parse)
        final boolean[] preferences = new boolean[3];

        // %pref{} (Preferences)
        input = process(PREF, input, raw -> raw.toLowerCase().replace(" ", "").replace("\n", ""), tags ->
        {
            for (String tag : tags)
            {
                switch (tag)
                {
                    case "ps": preferences[0] = true; continue;
                    case "rc": preferences[1] = true; continue;
                    case "no": preferences[2] = true; continue;
                    default: return "Error: PREF标签" + tag + "未识别";
                }
            }
            return "";
        });

        // Actually implementing the settings.
        if (!preferences[0]) input = trimSpaces(input);
        if (preferences[1]) input = escape(input);
        if (preferences[2]) return input;

        // %ac{} (Append chars)
        input = process(AC, input, tags -> repeat(tags[0], parseInt(tags[1])));

        // %as{} (Append chars with Separator)
        input = process(AS, input, tags -> repeat(tags[0], parseInt(tags[1]), tags[2]));

        // %ri{} (Random int)
        input = process(RI, input, tags -> getRandom(parseInt(tags[0]), parseInt(tags[1])));

        // %rd{} (Random double)
        input = process(RD, input, tags -> round(getRandom(parseDouble(tags[0]), parseDouble(tags[1])),
                tags.length == 3 ? parseInt(tags[2]) : 2));

        // %rs{} (Random string)
        input = process(RS, input, tags -> tags[getRandom(0, tags.length - 1)]);

        // %rp{} (Random strings with defined possibility)
        input = process(RP, input, MapUtils::getRandom);

        // %cb{} (Insert raw Char Before processing complex)
        input = processRaw(CB, input, raw -> raw);

        // Process complex things.
        input =  safeMode ? resolveComplexSafe(input) : resolveComplexJS(input);

        // %ca{} (Insert raw Char After processing complex)
        input = processRaw(CA, input, raw -> raw);

        // Return result
        return input;
    }
}
