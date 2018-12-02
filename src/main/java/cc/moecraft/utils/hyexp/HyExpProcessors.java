package cc.moecraft.utils.hyexp;

/**
 * 此类由 Hykilpikonna 在 2018/11/20 创建!
 * Created by Hykilpikonna on 2018/11/20!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
class HyExpProcessors
{
    interface HyExpPreProcessor
    {
        String preProcess(String raw);
    }

    interface HyExpProcessor
    {
        Object process(String[] tags);
    }

    interface HyExpRawProcessor
    {
        Object process(String raw);
    }

    interface HyExpDoer
    {
        void process(String[] tags);
    }
}
