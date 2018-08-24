package cc.moecraft.utils;

import java.util.Random;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
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

}