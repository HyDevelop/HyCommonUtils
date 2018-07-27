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
 * Meow!
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

    /**
     * 获取Getter
     * @param field 变量名
     * @param object 对象
     * @return Getter方法
     */
    public static Method getGetter(Field field, Object object)
    {
        return getGetterOrSetter(field, object, "get");
    }

    /**
     * 获取Setter
     * @param field 变量名
     * @param object 对象
     * @return Setter方法
     */
    public static Method getSetter(Field field, Object object)
    {
        return getGetterOrSetter(field, object, "set");
    }

    /**
     * 获取一个对象类里面定义的Get或者Set方法
     * @param field 变量名
     * @param object 对象
     * @param getOrSet 如果是"get"获取的就是Getter, 如果是"set"获取的就是Setter
     * @return Get方法或者Set方法
     */
    public static Method getGetterOrSetter(Field field, Object object, String getOrSet)
    {
        for (Method method : object.getClass().getMethods())
        {
            if (method.getName().startsWith(getOrSet))
            {
                String methodName = method.getName().toLowerCase();

                methodName = methodName.replaceFirst(getOrSet, "");

                if (methodName.equals(field.getName().toLowerCase())) return method;
            }
        }
        return null;
    }

    /**
     * 输出对象所有值
     * @param object 对象
     */
    public static void printAllValue(Object object) throws IllegalAccessException
    {
        System.out.println("正在输出此对象所有值...");

        for (Field field : object.getClass().getDeclaredFields())
        {
            System.out.println(String.format("- %s = %s", field.getName(), getValue(field, object)));
        }

        System.out.println("输出完成!");
    }

    /**
     * 查看一个类是不是Gson的JsonPrimitive里面可以存的类的变量
     * @param targetClass 目标类
     * @return 是不是Primitive类
     */
    public static boolean isPrimitive(Class targetClass)
    {
        ArrayList<Class> listThatIsConsideredPrimitive = new ArrayList<>();
        listThatIsConsideredPrimitive.add(Boolean.class);
        listThatIsConsideredPrimitive.add(String.class);
        listThatIsConsideredPrimitive.add(Character.class);
        listThatIsConsideredPrimitive.add(Byte.class);
        listThatIsConsideredPrimitive.add(Short.class);
        listThatIsConsideredPrimitive.add(Integer.class);
        listThatIsConsideredPrimitive.add(Long.class);
        listThatIsConsideredPrimitive.add(Float.class);
        listThatIsConsideredPrimitive.add(Double.class);

        return targetClass.isPrimitive() || listThatIsConsideredPrimitive.contains(targetClass);
    }

    /**
     * 判断一个类是不是数字类
     *
     * @param targetClass 目标类
     * @return 是不是数字类
     */
    public static boolean isNumeric(Class targetClass)
    {
        ArrayList<Class> listThatIsConsideredNumeric = new ArrayList<>();
        listThatIsConsideredNumeric.add(Byte.class);
        listThatIsConsideredNumeric.add(Short.class);
        listThatIsConsideredNumeric.add(Integer.class);
        listThatIsConsideredNumeric.add(Long.class);
        listThatIsConsideredNumeric.add(Float.class);
        listThatIsConsideredNumeric.add(Double.class);
        listThatIsConsideredNumeric.add(byte.class);
        listThatIsConsideredNumeric.add(short.class);
        listThatIsConsideredNumeric.add(int.class);
        listThatIsConsideredNumeric.add(long.class);
        listThatIsConsideredNumeric.add(float.class);
        listThatIsConsideredNumeric.add(double.class);

        return listThatIsConsideredNumeric.contains(targetClass);
    }
}
