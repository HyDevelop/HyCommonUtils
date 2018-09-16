package cc.moecraft.utils;

import cc.moecraft.utils.CodeFormatUtils.Indent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Data @AllArgsConstructor
public class ClassStringBuilder
{
    private String thePackage;
    private String className;
    private ArrayList<BuildVariable> variables = new ArrayList<>();
    private ArrayList<BuildMethod> methods = new ArrayList<>();

    @Override
    public String toString()
    {
        Indent indent = new Indent();
        StringBuilder result = new StringBuilder();

        /*
        例子:

        [package cc.moecraft.test;

        public class HelloWorld
        {
        ]
         */
        result.append("package ").append(thePackage).append(";").append("\n")
                .append("\n")
                .append("public class ").append(className).append("\n")
                .append("{").append("\n")
        ;

        indent.add();

        /*
        例子:

        [package cc.moecraft.test;

        public class HelloWorld
        {
            java.lang.String variableName = "default";
            java.lang.Integer anIntVariable;
        ]
         */
        for (BuildVariable variable : variables)
        {
            result.append(indent).append(variable).append("\n");
        }

        result.append("\n");


        /*
        例子:

        [package cc.moecraft.test;

        public class HelloWorld
        {
            java.lang.String variableName = "default";
            java.lang.Integer anIntVariable;

            public void run(java.lang.String param1)
            {
                System.out.print(param1);
            }
        ]
         */
        for (BuildMethod method : methods)
        {
            String methodInString = method.toString();

            for (String line : methodInString.split("\n"))
            {
                result.append(indent).append(line).append("\n");
            }
        }

        /*
        例子:

        package cc.moecraft.test;

        public class HelloWorld
        {
            java.lang.String variableName = "default";
            java.lang.Integer anIntVariable;

            public void run(java.lang.String param1)
            {
                System.out.print(param1);
            }
        }
         */
        result.append("}");

        return result.toString();
    }

    public ClassStringBuilder(String thePackage, String className)
    {
        this(thePackage, className, new ArrayList<>(), new ArrayList<>());
    }

    @Data @AllArgsConstructor
    public static class BuildMethod
    {
        private ArrayList<Modifier> modifiers;
        private String name;
        private String returnType;
        private ArrayList<BuildParam> params;
        private ArrayList<String> lines;

        public BuildMethod(String name, Class<?> returnType)
        {
            this(new ArrayList<>(Collections.singletonList(Modifier.PUBLIC)), name,
                    returnType == null ? "void" : getClassPackageName(returnType),
                    new ArrayList<>(), new ArrayList<>());
        }

        public BuildMethod(String name, String returnType)
        {
            this(new ArrayList<>(Collections.singletonList(Modifier.PUBLIC)), name,
                    returnType == null || returnType.equals("") ? "void" : returnType,
                    new ArrayList<>(), new ArrayList<>());
        }

        @Override
        public String toString()
        {
            Indent indent = new Indent();
            StringBuilder result = new StringBuilder();

            {
                StringBuilder modifiersString = new StringBuilder();

                for (Modifier modifier : modifiers)
                    modifiersString.append(modifier.name().toLowerCase()).append(" ");

                // 例子: [public final ]
                result.append(modifiersString);
            }

            // 例子: [public final java.lang.String doSomething(]
            result.append(returnType == null ? "void" : returnType).append(" ").append(name).append("(");;

            if (params.size() > 1)
            {
                StringBuilder paramsString = new StringBuilder();

                paramsString.append(params.get(0).toString());

                for (int i = 1; i < params.size(); i++)
                    paramsString.append(", ").append(params.get(i).toString());

                // 例子: [public final java.lang.String doSomething(java.lang.String paramString1, java.lang.Integer paramInt1]
                result.append(paramsString);
            }

            // 例子:
            // [public final java.lang.String doSomething(java.lang.String paramString1, java.lang.Integer paramInt1)
            // {
            // ]
            result.append(") \n{\n");
            indent.add();

            {
                StringBuilder linesString = new StringBuilder();

                for (String line : lines)
                    linesString.append(indent).append(line).append("\n");

                // 例子:
                // [public final java.lang.String doSomething(java.lang.String paramString1, java.lang.Integer paramInt1)
                // {
                //     System.out.println("test");
                //     return paramString1 + paramInt1;
                // ]
                result.append(linesString);
            }

            // 例子:
            // [public final java.lang.String doSomething(java.lang.String paramString1, java.lang.Integer paramInt1)
            // {
            //     System.out.println("test");
            //     return paramString1 + paramInt1;
            // }]
            result.append("}");

            return result.toString();
        }

        public enum Modifier
        {
            PUBLIC, PRIVATE, STATIC, FINAL
        }
    }

    @Data @AllArgsConstructor
    public static class BuildParam
    {
        private String name;
        private Class<?> type;

        @Override
        public String toString()
        {
            return getClassPackageName(type) + " " + name;
        }
    }

    @Data @AllArgsConstructor
    public static class BuildVariable
    {
        private String name;
        private Class<?> type;
        private String defaultValue;

        public BuildVariable(String name, Class<?> type)
        {
            this(name, type, "");
        }

        @Override
        public String toString()
        {
            // 例子: [java.lang.String variableName]
            String result = getClassPackageName(type) + " " + name;

            // 例子: [java.lang.String variableName = "default"]
            if (defaultValue != null && !defaultValue.isEmpty()) result += " = " + defaultValue;

            // 例子: [java.lang.String variableName = "default";]
            result += ";";

            return result;
        }
    }

    public static String getClassPackageName(Class type)
    {
        return type.getName();
    }
}
