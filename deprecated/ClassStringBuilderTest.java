import cc.moecraft.utils.ClassStringBuilder;

import static cc.moecraft.utils.ClassStringBuilder.*;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ClassStringBuilderTest
{
    public static void main(String[] args)
    {
        // 创建类构造器
        ClassStringBuilder classStringBuilder = new ClassStringBuilder(
                "cc.moecraft.test",
                "HelloWorld");

        // 创建一个变量
        BuildVariable variable1 = new BuildVariable(
                "debug", Boolean.class,
                "true");

        // 添加这个变量
        classStringBuilder.getVariables().add(variable1);

        // 创建一个方法
        BuildMethod method1 = new ClassStringBuilder.BuildMethod(
                "run",
                "");

        // 方法里添加一行
        method1.getLines().add("System.out.println(\"HelloWorld\");");

        // 添加这个方法
        classStringBuilder.getMethods().add(method1);

        // 输出
        System.out.println(classStringBuilder.toString());
    }
}
