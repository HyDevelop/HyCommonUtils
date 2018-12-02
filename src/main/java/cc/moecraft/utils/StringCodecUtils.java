package cc.moecraft.utils;

import cn.hutool.core.codec.Base32;

/**
 * 此类由 Hykilpikonna 在 2018/06/13 创建!
 * Created by Hykilpikonna on 2018/06/13!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class StringCodecUtils
{
    private static final String HEX_STRING = "0123456789ABCDEF";
    private static final char[] HEX_CHARS = HEX_STRING.toCharArray();

    /**
     * 字符串转换成十六进制字符串
     * Convert a string to a hex string.
     *
     * @param original 源字符串 / Original String
     * @param spacing 是否有空格 / Is there space in between.
     * @return HEX字符串 / Hex String.
     */
    public static String toHex(String original, boolean spacing)
    {
        StringBuilder result = new StringBuilder();

        for (byte b : original.getBytes())
        {
            result.append(HEX_CHARS[(b & 0x0f0) >> 4]).append(HEX_CHARS[b & 0x0f]);
            if (spacing) result.append(' ');
        }

        return result.toString().trim();
    }

    /**
     *
     *
     * @param original
     * @return
     */
    public static String toHex(String original)
    {
        return toHex(original, false);
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr HEX字符串
     * @return 还原字符串
     */
    public static String fromHex(String hexStr)
    {
        hexStr = hexStr.replace(" ", "").replace("\n", "");

        byte[] bytes = new byte[hexStr.length() / 2];

        for (int i = 0; i < bytes.length; i++)
        {
            int n = HEX_STRING.indexOf(HEX_CHARS[2 * i]) * 16;
            n += HEX_STRING.indexOf(HEX_CHARS[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 字符串转换成Base32
     *
     * @param original 源字符串
     * @return Base32字符串
     */
    public static String toBase32(String original)
    {
        return Base32.encode(original);
    }

    /**
     * Base32转换回字符串
     *
     * @param base32 Base32字符串
     * @return 还原字符串
     */
    public static String fromBase32(String base32)
    {
        return Base32.decodeStr(base32);
    }

    /**
     * 字符串转换成ASCII串
     * @param original 源字符串
     * @return ASCII串
     */
    public static String toAscii(String original)
    {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = original.toCharArray();
        for (int i = 0; i < chars.length - 1; i++)
        {
            stringBuilder.append((int) chars[i]).append(" ");
        }
        stringBuilder.append((int) chars[chars.length - 1]);
        return stringBuilder.toString();
    }

    /**
     * ASCII串转字符串
     * @param ascii ASCII串 (空格分割)
     * @return 还原字符串
     */
    public static String fromAscii(String ascii)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] chars = ascii.split(" ");
        for (String aChar : chars)
        {
            stringBuilder.append((char) Integer.parseInt(aChar));
        }
        return stringBuilder.toString();
    }
}
