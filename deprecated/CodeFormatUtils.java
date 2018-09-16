package cc.moecraft.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 此类由 Hykilpikonna 在 2018/07/11 创建!
 * Created by Hykilpikonna on 2018/07/11!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class CodeFormatUtils
{
    @NoArgsConstructor
    public static class Indent
    {
        private int amount = 0;

        public Indent add()
        {
            amount += 4;
            return this;
        }

        public Indent reduce()
        {
            amount -= 4;
            return this;
        }

        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < amount; i++) result.append(" ");
            return result.toString();
        }
    }
}
