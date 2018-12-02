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
@SuppressWarnings({"WeakerAccess", "unused"})
public class StringCodecUtils
{
    private static final String HEX_STRING = "0123456789ABCDEF";
    private static final char[] HEX_CHARS = HEX_STRING.toCharArray();

    /**
     * Convert a string to a hex string.
     *
     * @param original Original String
     * @param spacing Is there space in between.
     * @return Hex String.
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
     * Convert a string to a hex string.
     *
     * @param original Original String
     * @return Hex String.
     */
    public static String toHex(String original)
    {
        return toHex(original, true);
    }

    /**
     * Decode a hex string to it's original string.
     *
     * @param hexStr Hex string
     * @return Original string.
     */
    public static String fromHex(String hexStr)
    {
        hexStr = hexStr.replace(" ", "").replace("\n", "");

        char[] chars = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];

        for (int i = 0; i < bytes.length; i++)
        {
            int start = 2 * i;
            int n = HEX_STRING.indexOf(chars[start]) * 16 + HEX_STRING.indexOf(chars[start + 1]);
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
