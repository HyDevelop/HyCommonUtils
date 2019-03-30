package cc.moecraft.utils.hyexp;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@Getter
enum HyExpPattern
{
    RI("(?ms)(?<=%ri\\{)[-0-9,.]*?(?=})", "(?ms)%ri\\{[-0-9,.]*?}"),
    RD("(?ms)(?<=%rd\\{)[-0-9,.]*?(?=})", "(?ms)%rd\\{[-0-9,.]*?}"),
    RS("(?ms)(?<=%rs\\{).*?(?=})", "(?ms)%rs\\{.*?}"),
    RP("(?ms)(?<=%rp\\{).*?(?=})", "(?ms)%rp\\{.*?}"),
    AC("(?ms)(?<=%ac\\{).*?(?=})", "(?ms)%ac\\{.*?}"),
    AS("(?ms)(?<=%as\\{).*?(?=})", "(?ms)%as\\{.*?}"),
    CB("(?ms)(?<=%cb\\{).*?(?=})", "(?ms)%cb\\{.*?}"),
    CA("(?ms)(?<=%ca\\{).*?(?=})", "(?ms)%ca\\{.*?}"),
    PREF("(?ms)(?<=%pref\\{).*?(?=})", "(?ms)%pref\\{.*?}");

    final Pattern find;
    final Pattern replace;

    HyExpPattern(String find, String replace)
    {
        this.find = Pattern.compile(find);
        this.replace = Pattern.compile(replace);
    }
}
