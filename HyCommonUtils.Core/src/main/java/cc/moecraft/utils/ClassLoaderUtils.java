package cc.moecraft.utils;

/**
 * 此类由 Hykilpikonna 在 2018/09/16 创建!
 * Created by Hykilpikonna on 2018/09/16!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class ClassLoaderUtils
{
    public static ClassLoader getClassLoader()
    {
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        return context != null ? context : ClassLoaderUtils.class.getClassLoader();
    }
}
