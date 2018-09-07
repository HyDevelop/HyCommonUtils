package cc.moecraft.utils;

import lombok.*;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 *
 * 复杂格式:
 * - 简单计算:            %cal{算式}
 * - 定义变量:            %var{变量名,值} // 可以覆盖
 * - 使用变量:            %val{变量名}
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
            private static final Pattern ri = Pattern.compile("(?<=%ri\\{)[-0-9,.]*?(?=})");
            private static final Pattern rd = Pattern.compile("(?<=%rd\\{)[-0-9,.]*?(?=})");
            private static final Pattern rs = Pattern.compile("(?<=%rs\\{).*?(?=})");
            private static final Pattern rp = Pattern.compile("(?<=%rp\\{).*?(?=})");
        }

        private static final class replace
        {
            private static final Pattern ri = Pattern.compile("%ri\\{[-0-9,.]*?}");
            private static final Pattern rd = Pattern.compile("%rd\\{[-0-9,.]*?}");
            private static final Pattern rs = Pattern.compile("%rs\\{.*?}");
            private static final Pattern rp = Pattern.compile("%rp\\{.*?}");
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
        Matcher matcher;

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

        return resolveComplex(raw, new ScriptEngineManager().getEngineByName("js"));
    }

    private static String resolveComplex(String raw, ScriptEngine engine)
    {
        // complex, 这些是按字符顺序执行的.
        for (int i = 0; i < raw.length(); i++)
        {
            char oneChar = raw.charAt(i);
            if (oneChar == '%')
            {
                String operationInString = "" + raw.charAt(i + 1) + raw.charAt(i + 2) + raw.charAt(i + 3);
                Operation operation;

                try
                {
                    operation = Operation.valueOf(operationInString.toLowerCase());
                }
                catch (Exception e) { continue; }

                i += 4;

                // 如果有内部大括号先解析内部
                String inBracketsRaw = StringUtils.findBrackets(raw.substring(i, raw.length() - 1));
                String inBrackets = resolveComplex(inBracketsRaw, engine);
                String[] split = inBrackets.split(",");
                String rawMatch = "%" + operationInString + "{" + inBracketsRaw + "}";

                switch (operation)
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

    /**
     * 获取格式标签
     * @param original 源字符串
     * @param pattern 格式
     * @return 标签数组
     */
    private static ArrayList<String> getTags(String original, Pattern pattern)
    {
        ArrayList<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(original);

        while (matcher.find()) result.add(matcher.group());
        return result;
    }

    private static ArrayList<String[]> getSplitTags(String original, Pattern pattern)
    {
        ArrayList<String> tags = getTags(original, pattern);
        ArrayList<String[]> result = new ArrayList<>();
        tags.forEach(tag -> result.add(tag.split(",")));

        return result;
    }

    @Data @AllArgsConstructor
    private static class OperationData implements Comparable<OperationData>
    {
        private int start;
        private Operation operation;
        private String value;

        @Override
        public int compareTo(OperationData o)
        {
            return start - o.start;
        }
    }

    private enum Operation
    {
        var, val, cal
    }
}
