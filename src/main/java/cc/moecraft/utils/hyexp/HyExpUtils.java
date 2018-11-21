package cc.moecraft.utils.hyexp;

import java.util.regex.Matcher;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
class HyExpUtils
{
    /**
     * Process a tag.
     *
     * @param pattern Pattern
     * @param original Original String.
     * @param processor Processor for processing the tag.
     * @return Result.
     */
    static String process(HyExpPattern pattern, String original, HyExpProcessor processor)
    {
        return process(pattern, original, raw -> raw, processor);
    }

    /**
     * Process a tag.
     *
     * @param pattern Pattern
     * @param original Original String.
     * @param preProcessor Processor before the tag is split.
     * @param processor Processor for processing the tag.
     * @return Result.
     */
    static String process(HyExpPattern pattern, String original, HyExpPreProcessor preProcessor, HyExpProcessor processor)
    {
        Matcher matcher = pattern.find.matcher(original);
        while (matcher.find()) original = pattern.replace.matcher(original).replaceFirst(
                String.valueOf(processor.process(preProcessor.preProcess(matcher.group()).split(","))));
        return original;
    }
}
