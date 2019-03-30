package cc.moecraft.utils;

import java.lang.reflect.Field;

/**
 * 此类由 Hykilpikonna 在 2018/09/16 创建!
 * Created by Hykilpikonna on 2018/09/16!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class ExceptionUtils
{
    /**
     * 把所有变量输出到字符串里
     * @param throwable 异常
     * @param <T> 类型
     * @return 字符串
     */
    public static <T extends Throwable> String getAllVariables(T throwable)
    {
        StringBuilder variables = new StringBuilder("@").append(throwable.getClass().getName()).append("[");

        for (Field field : throwable.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try
            {
                String name = field.getName();
                String value = field.get(throwable).toString();
                variables.append(name).append("=").append(value).append(";");
            }
            catch (IllegalAccessException ignored) {}
        }

        return variables.append("]").toString();
    }
}
