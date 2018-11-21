package cc.moecraft.utils;

import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.min;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class MathUtils
{
    /**
     * 四舍五入
     * @param original 源
     * @param decimals 小数点后几位 ( 0: 1 | 1: 0.1 | 4: 0.0001 )
     * @return 四舍五入之后的
     */
    public static double round(double original, int decimals)
    {
        double scale = pow(10d, decimals);
        return Math.round(original * scale) / scale;
    }

    /**
     * 获取随机数
     * @param min 最小
     * @param max 最大
     * @return 随机整数
     */
    public static int getRandom(int min, int max)
    {
        return new Random().nextInt(max + 1 - min) + min;
    }

    /**
     * 获取随机double
     * @param range1 最小
     * @param range2 最大
     * @return 随机double
     */
    public static double getRandom(double range1, double range2)
    {
        double min = min(range1, range2);
        double max = max(range1, range2);
        return min + (max - min) * new Random().nextDouble();
    }

    /**
     * 获取随机double
     * @param range1 最小
     * @param range2 最大
     * @param decimals 小数点后几位 ( 0: 1 | 1: 0.1 | 4: 0.0001 )
     * @return 随机double
     */
    public static double getRandom(double range1, double range2, int decimals)
    {
        double min = min(range1, range2);
        double max = max(range1, range2);
        return round(getRandom(min, max), decimals);
    }

    /**
     * 简单的计算字符串
     * @param expression 字符串
     * @return 计算结果
     */
    public static double simpleEval(final String expression)
    {
        return new Object()
        {
            int pos = -1, ch;

            void nextChar()
            {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat)
            {
                while (ch == ' ') nextChar();
                if (ch == charToEat)
                {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse()
            {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression()
            {
                double x = parseTerm();
                for (;;)
                {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm()
            {
                double x = parseFactor();
                for (;;)
                {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor()
            {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;

                if (eat('('))
                { // parentheses
                    x = parseExpression();
                    eat(')');
                }

                else if ((ch >= '0' && ch <= '9') || ch == '.')
                { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                }

                else throw new RuntimeException("Unexpected: " + (char)ch);

                if (eat('^')) x = pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
