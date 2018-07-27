package cc.moecraft.utils;

import java.util.Scanner;

/**
 * 此类由 Hykilpikonna 在 2018/04/24 创建!
 * Created by Hykilpikonna on 2018/04/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 */
public class StringUtils
{
    /**
     * 添加HTTP请求参数
     * @param builder 字符串构造器
     * @param key 键
     * @param value 值
     */
    public static StringBuilder addParameter(StringBuilder builder, String key, String value)
    {
        if (!builder.toString().endsWith("&") || !builder.toString().endsWith("?"))
        {
            builder.append("&");
        }
        return builder.append(key).append("=").append(value);
    }
}
