package cc.moecraft.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/12/01 创建!
 * Created by Hykilpikonna on 2018/12/01!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class MapUtils
{
    /**
     * Distribute chance for a map of chance.
     * The -1 value means the Max value to get random of.
     *
     * For example:
     *   sortChance(String.class, "One", 50, "Two", 25, "Three", 25)
     *
     * Will return:
     *   [(-1d, "100"), (50d, "One"), (75d, "Two"), (100d, "Three")]
     *
     * @param kv Key value pairs. (CHANCE IS AFTER VALUE)
     * @return Sorted hashMap. (CHANCE DISTRIBUTION IS BEFORE VALUE)
     */
    public static LinkedHashMap<Double, Object> distributeChance(Object... kv)
    {
        LinkedHashMap<Double, Object> result = new LinkedHashMap<>();

        // Sort them one after another.
        // e.g. 50, 25, 25 becomes 50, 75, 100
        double max = 0;
        for (int i = 0; i < kv.length; i += 2)
        {
            double chance = Double.valueOf(kv[i + 1].toString());
            if (chance <= 0) continue;

            max += chance;
            result.put(max, kv[i]);
        }

        return result;
    }
}
