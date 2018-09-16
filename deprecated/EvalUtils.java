package cc.moecraft.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

import static cc.moecraft.utils.ClassStringBuilder.*;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class EvalUtils
{
    // 计划:
    //   先用variables做一个带变量的类,
    //   然后放进去一个需要用所有变量的构造器,
    //   然后把这个expression放进run()方法
    //   然后动态加载这个类,
    //   eval的时候反射创建实例, 反射注入对象
    //   然后反射执行run方法
    //   这样就可以带着所有变量eval了

    public static ClassStringBuilder createEvalClass(String expression, BuildVariable ... variables)
    {
        // 由于java最小的执行单元是类，如果想要执行该语句，必须为其构建一个完整的类
        String className = "C" + System.nanoTime();

        // 声明类
        ClassStringBuilder classBuilder = new ClassStringBuilder(
                "temp." + className + ".pkg", className);

        // 声明对象
        for (BuildVariable variable : variables) classBuilder.getVariables().add(variable);

        // run方法
        BuildMethod run = new BuildMethod("run", "");
        for (String line : expression.split("\n")) run.getLines().add(line);
        classBuilder.getMethods().add(run);

        // construct方法
        BuildMethod construct = new BuildMethod("construct", className);
        construct.getModifiers().add(BuildMethod.Modifier.STATIC);
        for (BuildVariable variable : variables)
        {
            construct.getParams().add(new BuildParam(variable.getName(), variable.getType()));
            construct.getLines().add(String.format("this.%s = %s;", variable.getName(), variable.getName()));
        }
        classBuilder.getMethods().add(construct);

        // 输出
        return classBuilder;
    }

    public static void buildEvalClass(ClassStringBuilder evalClass) throws IOException, ClassNotFoundException
    {
        // 将拼接好的字符串保存到一个java文件里
        String className = evalClass.getClassName();

        String javaFileName = className + ".java";
        File javaFile = new File(javaFileName);

        String classFileName = className + ".class";
        File classFile = new File(classFileName);

        OutputStream out = new FileOutputStream(javaFileName);
        out.write(evalClass.toString().getBytes());
        out.close();

        // 调用java进程来编译java文件
        Process javacProcess = Runtime.getRuntime().exec("javac " + javaFileName);

        // 读取javac进程中的错误流信息
        InputStream error = javacProcess.getErrorStream();

        // 读取流中的数据
        byte[] bytes = new byte[1024];

        // 对每一个文件里的内容进行复制
        int len = -1; // 表示已经读取了多少个字节，如果len返回-1，表示已经读到最后
        while ((len = error.read(bytes)) != -1)
        {
            String message = new String(bytes, 0, len);
            System.out.println(message);
        }

        // 关闭资源
        error.close();

        // 动态加载类
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {classFile.getParentFile().toURI().toURL()});
        urlClassLoader.loadClass(className);

        // TODO: 完成

        // 调用java进程来运行class
        // Process javaProcess=Runtime.getRuntime().exec("java " + className);

        // 读取java进程流中的信息
        // InputStream info = javaProcess.getInputStream();
        // while ((len = info.read(bytes)) != -1)
        // {
        //     String msg = new String(bytes, 0, len);
        //     System.out.println(msg);
        // }
        // info.close();

        // 删除java和class文
        javaFile.delete();
        classFile.delete();
    }
}
