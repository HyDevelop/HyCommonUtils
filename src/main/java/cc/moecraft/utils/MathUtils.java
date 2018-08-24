package cc.moecraft.utils;

import java.util.Random;

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
        double scale = Math.pow(10d, decimals);
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
     * @param min 最小
     * @param max 最大
     * @return 随机double
     */
    public static double getRandom(double min, double max)
    {
        return min + (max - min) * new Random().nextDouble();
    }

    /**
     * 获取随机double
     * @param min 最小
     * @param max 最大
     * @param decimals 小数点后几位 ( 0: 1 | 1: 0.1 | 4: 0.0001 )
     * @return 随机double
     */
    public static double getRandom(double min, double max, int decimals)
    {
        return round(getRandom(min, max), decimals);
    }

}
