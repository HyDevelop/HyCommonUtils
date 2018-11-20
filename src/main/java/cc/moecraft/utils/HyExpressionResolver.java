package cc.moecraft.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.MULTILINE;

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
public class HyExpressionResolver
{
    private static final class patterns
    {
        private static final class find
        {
            private static final Pattern ri = Pattern.compile("(?ms)(?<=%ri\\{)[-0-9,.]*?(?=})");
            private static final Pattern rd = Pattern.compile("(?ms)(?<=%rd\\{)[-0-9,.]*?(?=})");
            private static final Pattern rs = Pattern.compile("(?ms)(?<=%rs\\{).*?(?=})");
            private static final Pattern rp = Pattern.compile("(?ms)(?<=%rp\\{).*?(?=})");

            private static final Pattern pref = Pattern.compile("(?ms)(?<=%pref\\{).*?(?=})");
        }

        private static final class replace
        {
            private static final Pattern ri = Pattern.compile("(?ms)%ri\\{[-0-9,.]*?}");
            private static final Pattern rd = Pattern.compile("(?ms)%rd\\{[-0-9,.]*?}");
            private static final Pattern rs = Pattern.compile("(?ms)%rs\\{.*?}");
            private static final Pattern rp = Pattern.compile("(?ms)%rp\\{.*?}");

            private static final Pattern pref = Pattern.compile("(?ms)%pref\\{.*?}");
        }
    }

    @Getter @Setter
    private boolean safeMode = true;

    /**
     * 封装解析一条HyExp, 默认安全模式
     * @param raw 表达式字符串
     * @return 解析后的结果
     */
    public String resolve(String raw)
    {
        return resolve(raw, safeMode);
    }

    /**
     * 解析一条HyExp
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

        return safeMode ? resolveComplexSafe(raw, new HashMap<>()) : resolveComplexJS(raw, new ScriptEngineManager().getEngineByName("js"));
    }

    private static String resolveComplexSafe(String raw, Map<String, Argument> variables)
    {
        // complex, 这些是按字符顺序执行的.
        for (int i = 0; i < raw.length(); i++)
        {
            char oneChar = raw.charAt(i);
            if (oneChar != '%') continue;

            String operationInString = getOperationInString(raw, i);
            Operation operation = getOperation(operationInString);
            if (operation == null) continue;

            i += 4;

            // 如果有内部大括号先解析内部
            String inBracketsRaw = StringUtils.findBrackets(raw.substring(i, raw.length() - 1));
            String inBrackets = resolveComplexSafe(inBracketsRaw, variables);
            String[] split = inBrackets.split(",");
            String rawMatch = "%" + operationInString + "{" + inBracketsRaw + "}";

            return resolveComplexSafe(raw.replace(rawMatch, resolveComplexValueSafe(operation, split, variables, inBrackets)), variables);
        }

        return raw;
    }

    private static String resolveComplexValueSafe(Operation operation, String[] split, Map<String, Argument> variables, String inBrackets)
    {
        try
        {
            switch (operation)
            {
                case var:
                {
                    String name = split[0];
                    String value = ArrayUtils.getTheRestArgsAsString(new ArrayList<>(Arrays.asList(split)), 1, "");
                    String expression = name + " = " + value;
                    variables.put(name, new Argument(expression));
                    return "";
                }
                case val: return String.valueOf(variables.get(inBrackets).getArgumentValue());
                case cal: return String.valueOf(new Expression(inBrackets, toArray(variables)).calculate());
                default: return "";
            }
        }
        catch (Throwable e)
        {
            return "[ERR: " + e.getMessage() + "]";
        }
    }

    private static String resolveComplexJS(String raw, ScriptEngine engine)
    {
        // complex, 这些是按字符顺序执行的.
        for (int i = 0; i < raw.length(); i++)
        {
            char oneChar = raw.charAt(i);
            if (oneChar != '%') continue;

            String operationInString = getOperationInString(raw, i);
            Operation operation = getOperation(operationInString);
            if (operation == null) continue;

            i += 4;

            // 如果有内部大括号先解析内部
            String inBracketsRaw = StringUtils.findBrackets(raw.substring(i, raw.length() - 1));
            String inBrackets = resolveComplexJS(inBracketsRaw, engine);
            String[] split = inBrackets.split(",");
            String rawMatch = "%" + operationInString + "{" + inBracketsRaw + "}";

            return resolveComplexJS(raw.replace(rawMatch, resolveComplexValueJS(operation, split, engine, inBrackets)), engine);
        }

        return raw;
    }

    private static String resolveComplexValueJS(Operation operation, String[] split, ScriptEngine engine, String inBrackets)
    {
        try
        {
            switch (operation)
            {
                case var: engine.eval("var " + split[0] + " = " + ArrayUtils.getTheRestArgsAsString(new ArrayList<>(Arrays.asList(split)), 1, ""));
                default: return "";
                case val: return engine.get(inBrackets).toString();
                case cal: return engine.eval(inBrackets).toString();
            }
        }
        catch (ScriptException e)
        {
            return "[ERR: " + e.getMessage() + "]";
        }
    }

    private enum Operation
    {
        var, val, cal
    }

    private static Argument[] toArray(Map<String, Argument> variables)
    {
        Argument[] result = new Argument[variables.size()];

        int i = 0;
        for (Map.Entry<String, Argument> entry : variables.entrySet())
        {
            result[i] = entry.getValue();
            i++;
        }

        return result;
    }

    private static String getOperationInString(String raw, int i)
    {
        return  "" + raw.charAt(i + 1) + raw.charAt(i + 2) + raw.charAt(i + 3);
    }

    private static Operation getOperation(String operationInString)
    {
        try
        {
            return Operation.valueOf(operationInString.toLowerCase());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
