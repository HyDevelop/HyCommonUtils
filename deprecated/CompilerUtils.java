package cc.moecraft.utils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CompilerUtils
{
    /**
     * 获取系统的java编译器
     * @return java编译器
     */
    public static JavaCompiler getCompiler()
    {
        return ToolProvider.getSystemJavaCompiler();
    }
}
