package cc.moecraft.utils.hyexp;

import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
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
        static final Find find = new Find();
        static final class Find
        {
            static final Pattern ri = Pattern.compile("(?ms)(?<=%ri\\{)[-0-9,.]*?(?=})");
            static final Pattern rd = Pattern.compile("(?ms)(?<=%rd\\{)[-0-9,.]*?(?=})");
            static final Pattern rs = Pattern.compile("(?ms)(?<=%rs\\{).*?(?=})");
            static final Pattern rp = Pattern.compile("(?ms)(?<=%rp\\{).*?(?=})");

            static final Pattern ac = Pattern.compile("(?ms)(?<=%ac\\{).*?(?=})");
            static final Pattern cp = Pattern.compile("(?ms)(?<=%cp\\{).*?(?=})");
            static final Pattern ca = Pattern.compile("(?ms)(?<=%ca\\{).*?(?=})");

            static final Pattern pref = Pattern.compile("(?ms)(?<=%pref\\{).*?(?=})");
        }

        static final Replace replace = new Replace();
        static final class Replace
        {
            static final Pattern ri = Pattern.compile("(?ms)%ri\\{[-0-9,.]*?}");
            static final Pattern rd = Pattern.compile("(?ms)%rd\\{[-0-9,.]*?}");
            static final Pattern rs = Pattern.compile("(?ms)%rs\\{.*?}");
            static final Pattern rp = Pattern.compile("(?ms)%rp\\{.*?}");

            static final Pattern ac = Pattern.compile("(?ms)%ac\\{.*?}");
            static final Pattern cp = Pattern.compile("(?ms)%cp\\{.*?}");
            static final Pattern ca = Pattern.compile("(?ms)%ca\\{.*?}");

            static final Pattern pref = Pattern.compile("(?ms)%pref\\{.*?}");
        }
    }
}
