package cc.moecraft.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 此类由 Hykilpikonna 在 2017/09/13 创建!
 * Created by Hykilpikonna on 2017/09/13!
 * Twitter: @Hykilpikonna
 * QQ/Wechat: 565656
 */
public class FileUtils 
{
    /**
     * 创建文件
     * @param file 文件
     * @return 是否成功
     */
    public static boolean createFile(File file)
    {
        return createFile(file, true);
    }

    /**
     * 创建文件
     * @param file 文件
     * @param logging 是否输出日志
     * @return 是否成功
     */
    public static boolean createFile(File file, boolean logging)
    {
        File absoluteFile = file.getAbsoluteFile();

        // ConfigLibTest.getLogger().debug("File = " + absoluteFile.toString());
        // ConfigLibTest.getLogger().debug("AbsoluteFile = " + absoluteFile.toString());

        if(absoluteFile.exists())
        {
            if (logging) System.out.println("创建文件 " + absoluteFile + " 失败: 已存在");
            return false;
        }
        if (absoluteFile.toString().endsWith(File.separator))
        {
            if (logging) System.out.println("创建文件 " + absoluteFile + " 失败: 目标文件不能为目录");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!absoluteFile.getParentFile().exists())
        {
            //如果目标文件所在的目录不存在，则创建父目录
            //tempDebug("目标文件所在目录不存在, 正在创建...");
            if(!absoluteFile.getParentFile().mkdirs())
            {
                if (logging) System.out.println("创建文件 " + absoluteFile + " 失败: 创建目标文件所在目录失败");
                return false;
            }
        }
        //创建目标文件
        try
        {
            if (absoluteFile.createNewFile())
            {
                //tempDebug(GREEN + "创建文件" + file + "成功");
                return true;
            }
            else
            {
                if (logging) System.out.println("创建文件 " + absoluteFile + " 失败: 原因未知");
                return false;
            }
        }
        catch (Exception e)
        {
            if (logging) System.out.println("创建文件 " + absoluteFile + " 失败, 原因如下:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建目录
     * @param destDirName 目录
     * @return 是否成功
     */
    public static boolean createDir(String destDirName)
    {
        return createDir(destDirName,  false);
    }

    /**
     * 创建目录
     * @param destDirName 目录
     * @param logging 是否输出日志
     * @return 是否成功
     */
    public static boolean createDir(String destDirName, boolean logging)
    {
        File dir = new File(destDirName);
        if (dir.exists())
        {
            if (logging) System.out.println("创建目录 " + dir.getAbsolutePath() + " 失败: 已存在");
            return false;
        }
        //创建目录
        if (dir.mkdirs()) return true;
        else
        {
            if (logging) System.out.println("创建目录 " + dir.getAbsolutePath() + " 失败: 未知原因");
            return false;
        }
    }

    public static String combinePath(String path1, String path2)
    {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    public static String createTempFile(String prefix, String suffix, String dirName)
    {
        File tempFile;
        if (dirName == null)
        {
            try
            {
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                //tempDebug("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
        else
        {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists())
            {
                if (!createDir(dirName))
                {
                    //tempDebug("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try
            {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                //tempDebug("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }

    public static void copy(File source, File dest) throws IOException
    {
        Files.copy(source.toPath(), dest.toPath());
    }

    /**
     * 递归获取所有文件夹下的所有文件
     *
     * @param existingList 已经有的列表
     * @param path 根目录
     * @return 所有文件
     */
    public static ArrayList<File> getAllFiles(ArrayList<File> existingList, File path)
    {
        if (path.isFile())
        {
            if (!existingList.contains(path)) existingList.add(path);
            return existingList;
        }

        for (File file : Objects.requireNonNull(path.listFiles()))
        {
            existingList = getAllFiles(existingList, file);
        }

        return existingList;
    }

    public static ArrayList<File> getAllFiles(File path)
    {
        return getAllFiles(new ArrayList<>(), path);
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀
     */
    public static String getFileExtension(File file)
    {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    /**
     * 获取所有Java文件
     *
     * @param allFiles 所有文件
     * @return 所有java文件
     */
    public static ArrayList<File> getAllJavaFiles(ArrayList<File> allFiles)
    {
        ArrayList<File> filterResult = new ArrayList<>();

        allFiles.forEach(file ->
        {
            if (FileUtils.getFileExtension(file).equals("java")) filterResult.add(file);
        });

        return filterResult;
    }

    /**
     * 把一个文件的内容读入String
     *
     * @param path 路径
     * @param encoding 编码
     * @return String
     */
    public static String readFileAsString(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * 把一个文件的内容读入String
     *
     * @param file 路径
     * @return String
     */
    public static String readFileAsString(File file) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));

        StringBuilder stringBuilder = new StringBuilder();

        lines.forEach(line -> stringBuilder.append(line).append("\n"));

        return stringBuilder.toString();
    }

    /**
     * 从resources导出
     * @param resourceClass 带resources的类
     * @throws IOException 复制错误
     */
    public static void copyResource(Class resourceClass, String fileName, File toFile) throws IOException, NullPointerException
    {
        createDir(toFile.getParent());
        InputStream resourceAsStream = resourceClass.getClassLoader().getResourceAsStream(fileName);
        Files.copy(resourceAsStream, Paths.get(toFile.getAbsolutePath()));
    }
}
