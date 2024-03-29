package cc.moecraft.utils;

import java.util.HashMap;

/**
 * 此类由 Hykilpikonna 在 2018/05/24 创建!
 * Created by Hykilpikonna on 2018/05/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@SuppressWarnings("ALL")
public class MapBuilder
{
    /**
     * 把KV对构建成Map
     * Build a Map with key value pairs.
     *
     * @param classOne 第一个类的类型 / Type of Key
     * @param classTwo 第二个类的类型 / Type of Value
     * @param <T1> 第一个类的类型 / Type of Key
     * @param <T2> 第二个类的类型 / Type of Value
     * @param kv KV对             / Key value pairs.
     * @return 构建好的Map        / A HashMap with the values.
     */
    public static <T1, T2> HashMap<T1, T2> build(Class<T1> classOne, Class<T2> classTwo, Object... kv)
    {
        HashMap<T1, T2> result = new HashMap<>();

        for (int i = 0; i < kv.length; i += 2)
        {
            T1 key = (T1) kv[i];
            T2 value = (T2) kv[i + 1];

            result.put(key, value);
        }

        return result;
    }

    /**
     * 预设构建
     * Presets
     */
    public static class presets
    {
        public static HashMap<String, String> buildStringString(Object... kv)
        {
            return build(String.class, String.class, kv);
        }

        public static HashMap<String, Object> buildStringObject(Object... kv)
        {
            return build(String.class, Object.class, kv);
        }

        public static HashMap<String, Long> buildStringLong(Object... kv)
        {
            return build(String.class, Long.class, kv);
        }

        public static HashMap<String, Double> buildStringDouble(Object... kv)
        {
            return build(String.class, Double.class, kv);
        }
    }
}
