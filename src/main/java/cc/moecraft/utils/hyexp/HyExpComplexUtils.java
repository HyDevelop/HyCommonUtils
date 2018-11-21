package cc.moecraft.utils.hyexp;

import cc.moecraft.utils.ArrayUtils;
import cc.moecraft.utils.StringUtils;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class HyExpComplexUtils
{
    static String resolveComplexSafe(String raw, Map<String, Argument> variables)
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

            return resolveComplexSafe(raw.replace(rawMatch, resolveComplexHelperSafe(operation, split, variables, inBrackets)), variables);
        }

        return raw;
    }

    static String resolveComplexHelperSafe(Operation operation, String[] split, Map<String, Argument> variables, String inBrackets)
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

    static String resolveComplexJS(String raw, ScriptEngine engine)
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

            return resolveComplexJS(raw.replace(rawMatch, resolveComplexHelperJS(operation, split, engine, inBrackets)), engine);
        }

        return raw;
    }

    private static String resolveComplexHelperJS(Operation operation, String[] split, ScriptEngine engine, String inBrackets)
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

    enum Operation
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
