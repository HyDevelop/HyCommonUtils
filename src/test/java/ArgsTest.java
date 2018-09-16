import cc.moecraft.utils.cli.Args;
import cc.moecraft.utils.cli.ArgsUtils;

/**
 * 此类由 Hykilpikonna 在 2018/09/16 创建!
 * Created by Hykilpikonna on 2018/09/16!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class ArgsTest
{
    public static void main(String[] rawArgs)
    {
        Args args = ArgsUtils.parse(rawArgs);
        System.out.println(args);
    }
}
