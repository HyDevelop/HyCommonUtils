package cc.moecraft.utils.hyexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
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
    static String process(Pattern pattern, String original, HyExpProcessor processor)
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
    static String process(Pattern pattern, String original, HyExpPreProcessor preProcessor, HyExpProcessor processor)
    {
        Matcher matcher = pattern.matcher(original);
        while (matcher.find()) original = processor.process(preProcessor.preProcess(matcher.group()).split(","));
        return original;
    }
}
