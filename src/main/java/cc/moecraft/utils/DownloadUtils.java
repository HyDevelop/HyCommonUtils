package cc.moecraft.utils;

import cn.hutool.http.HttpUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class DownloadUtils
{
    @Getter @Setter
    private static String defaultCharset = "utf-8";

    /**
     * 从URL获取JSON对象
     *
     * @param url URL
     * @param charset 字符集
     * @return JSON对象
     */
    public static JsonElement getJsonElementFromURL(String url, String charset)
    {
        return new JsonParser().parse(downloadAsString(url, charset));
    }

    /**
     * 从URL获取JSON对象
     *
     * @param url URL
     * @return JSON对象
     */
    public static JsonElement getJsonElementFromURL(String url)
    {
        return getJsonElementFromURL(url, defaultCharset);
    }

    /**
     * 下载HTTP数据为字符串
     *
     * @param url 下载地址
     * @param charset 字符集
     * @return 下载到的字符串
     */
    public static String downloadAsString(String url, String charset)
    {
        return HttpUtil.downloadString(url, Charset.forName(charset));
    }

    /**
     * 下载HTTP数据为字符串
     *
     * @param url 下载地址
     * @return 下载到的字符串
     */
    public static String downloadAsString(String url)
    {
        return downloadAsString(url, defaultCharset);
    }
}
