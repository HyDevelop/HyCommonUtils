package cc.moecraft.utils;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 此类由 Hykilpikonna 在 2018/07/04 创建!
 * Created by Hykilpikonna on 2018/07/04!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class TimeUtils
{
    public static String getCurrentTime(String pattern)
    {
        return new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime());
    }

    public static String getCurrentTime()
    {
        return getCurrentTime("yy-MM-dd HH:mm:ss");
    }

    /**
     * 转换时间为字符串
     *
     * 时间格式和Date的格式一样
     *
     * @param format 字符串格式
     * @param time 时间
     * @param timeUnit 时间单位
     * @return 字符串
     */
    public static String convertToString(String format, float time, TimeUnits timeUnit)
    {
        long millisTime = Math.round(time * TimeUnits.getTotalMultiplierBetweenTwoUnits(timeUnit, TimeUnits.Millis));

        Date date = new Date(millisTime);
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 时间单位
     */
    public enum TimeUnits
    {
        Nano    (0, -1,     null),
        Millis  (1, 1000,   Nano),
        Second  (2, 1000,   Millis),
        Minute  (3, 60,     Second),
        Hour    (4, 60,     Minute),
        Day     (5, 24,     Hour),
        Week    (6, 7,      Day),
        Month   (7, 30,     Week);

        @Getter
        int index, multiplier; // 到上一个单位的乘数
        @Getter
        TimeUnits previous; // 哪个是上个单位

        TimeUnits(int index, int multiplier, TimeUnits previous)
        {
            this.index = index;
            this.multiplier = multiplier;
            this.previous = previous;
        }

        /**
         * 获取两个单位之间的乘数
         * @param unit1 单位1
         * @param unit2 单位2
         * @return 总乘数
         */
        public static double getTotalMultiplierBetweenTwoUnits(TimeUnits unit1, TimeUnits unit2)
        {
            if (unit1 == unit2) return 1;

            TimeUnits larger = unit1.getIndex() > unit2.getIndex() ? unit1 : unit2;
            TimeUnits smaller = unit1.getIndex() < unit2.getIndex() ? unit1 : unit2;

            boolean inverted = smaller == unit1;

            double totalMultiplier = 1;

            while (true)
            {
                if (larger == smaller) return inverted ? 1d / totalMultiplier : totalMultiplier;

                totalMultiplier *= larger.getMultiplier();
                larger = larger.getPrevious();
            }
        }
    }
}
