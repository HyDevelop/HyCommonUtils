import cc.moecraft.utils.StringCodecUtils;

/**
 * 此类由 Hykilpikonna 在 2018/12/01 创建!
 * Created by Hykilpikonna on 2018/12/01!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class CodecTest
{
    public static void main(String[] args)
    {
        System.out.println(StringCodecUtils.toHex("123 Hello World! 中文"));
        System.out.println(StringCodecUtils.fromHex("31 32 33 20 48 65 6C 6C 6F 20 57 6F 72 6C 64 21 20 E4 B8 AD E6 96 87"));

        System.out.println(StringCodecUtils.toAscii("123 Hello World! 中文"));
        System.out.println(StringCodecUtils.fromAscii("49 50 51 32 72 101 108 108 111 32 87 111 114 108 100 33 32 20013 25991"));
    }
}
