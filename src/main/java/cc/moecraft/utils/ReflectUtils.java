package cc.moecraft.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 此类由 Hykilpikonna 在 2018/04/24 创建!
 * Created by Hykilpikonna on 2018/04/24!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class ReflectUtils
{
    /**
     * 反射获取变量值
     * @param field Field
     * @param object 变量
     * @return 值
     */
    public static Object getValue(Field field, Object object) throws IllegalAccessException
    {
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 获取JsonPrimitive的"getAs***()"方法
     * @param field Field
     * @param jsonPrimitive Json原始对象
     * @return 获取到的方法
     */
    public static Method getJsonPrimitiveGetAsMethod(Field field, JsonPrimitive jsonPrimitive)
    {
        String fieldSimpleName = field.getType().getSimpleName();

        for (Method method : jsonPrimitive.getClass().getMethods())
        {
            if (method.getName().startsWith("getAs"))
            {
                String methodName = method.getName();

                methodName = methodName.replaceFirst("getAs", "");

                if (methodName.equalsIgnoreCase(fieldSimpleName)) return method;
            }
        }
        return null;
    }
}
