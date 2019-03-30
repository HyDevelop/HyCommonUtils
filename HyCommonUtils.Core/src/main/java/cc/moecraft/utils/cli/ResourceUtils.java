package cc.moecraft.utils.cli;

import cc.moecraft.utils.ClassLoaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static cc.moecraft.utils.StringUtils.*;

/**
 * 此类由 Hykilpikonna 在 2018/09/16 创建!
 * Created by Hykilpikonna on 2018/09/16!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@SuppressWarnings("WeakerAccess")
public class ResourceUtils
{
    /**
     * 带变量输出一个resource文件
     * @param printer 日志输出类 (可以是匿名类)
     * @param name 文件名 / 路径
     * @param variables 变量和替换
     */
    public static void printResource(CustomPrinter printer, String name, Object... variables)
    {
        printResource(ClassLoaderUtils.getClassLoader(), printer, name, variables);
    }

    /**
     * 带变量输出一个resource文件
     * @param classLoader 带resource的类加载器
     * @param printer 日志输出类 (可以是匿名类)
     * @param name 文件名 / 路径
     * @param variables 变量和替换
     */
    public static void printResource(ClassLoader classLoader, CustomPrinter printer, String name, Object... variables)
    {
        print(printer, readResource(classLoader, name), variables);
    }

    /**
     * 带变量读取一个resource文件
     * @param classLoader 带resource的类加载器
     * @param name 文件名 / 路径
     */
    public static String readResource(ClassLoader classLoader, String name)
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(name))))
        {
            return readToString(reader);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
