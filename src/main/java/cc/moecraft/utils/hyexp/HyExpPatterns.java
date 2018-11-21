package cc.moecraft.utils.hyexp;

import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
class HyExpPatterns
{
    // Don't ask me why I write code like this.
    // It looks good and understandable. (?)

    static final Patterns patterns = new Patterns();
    static final class Patterns
    {
        final Find find = new Find();
        static final class Find
        {
            final Pattern ri = Pattern.compile("(?ms)(?<=%ri\\{)[-0-9,.]*?(?=})");
            final Pattern rd = Pattern.compile("(?ms)(?<=%rd\\{)[-0-9,.]*?(?=})");
            final Pattern rs = Pattern.compile("(?ms)(?<=%rs\\{).*?(?=})");
            final Pattern rp = Pattern.compile("(?ms)(?<=%rp\\{).*?(?=})");

            final Pattern ac = Pattern.compile("(?ms)(?<=%ac\\{).*?(?=})");
            final Pattern cp = Pattern.compile("(?ms)(?<=%cp\\{).*?(?=})");
            final Pattern ca = Pattern.compile("(?ms)(?<=%ca\\{).*?(?=})");

            final Pattern pref = Pattern.compile("(?ms)(?<=%pref\\{).*?(?=})");
        }

        final Replace replace = new Replace();
        static final class Replace
        {
            final Pattern ri = Pattern.compile("(?ms)%ri\\{[-0-9,.]*?}");
            final Pattern rd = Pattern.compile("(?ms)%rd\\{[-0-9,.]*?}");
            final Pattern rs = Pattern.compile("(?ms)%rs\\{.*?}");
            final Pattern rp = Pattern.compile("(?ms)%rp\\{.*?}");

            final Pattern ac = Pattern.compile("(?ms)%ac\\{.*?}");
            final Pattern cp = Pattern.compile("(?ms)%cp\\{.*?}");
            final Pattern ca = Pattern.compile("(?ms)%ca\\{.*?}");

            final Pattern pref = Pattern.compile("(?ms)%pref\\{.*?}");
        }
    }
}
