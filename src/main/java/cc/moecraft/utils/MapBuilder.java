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
     * @param classOne 第一个类的类型
     * @param classTwo 第二个类的类型
     * @param <T1> 第一个类的类型
     * @param <T2> 第二个类的类型
     * @param kv KV对
     * @return 构建好的Map
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
}
