package cc.moecraft.utils;

import cc.moecraft.utils.hyexp.HyExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * 这个类只是为了兼容以前的接口才做的, 推荐直接用HyExpression类
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
@Deprecated
public class HyExpressionResolver
{
    @Getter @Setter
    private boolean safeMode = true;

    /**
     * 封装解析一条HyExp, 默认安全模式
     *
     * @param raw 表达式字符串
     * @return 解析后的结果
     * @deprecated 移动到了HyExpression类
     */
    @Deprecated
    public String resolve(String raw)
    {
        return HyExpression.resolve(raw, safeMode);
    }

    /**
     * 解析一条HyExp
     *
     * @param raw 表达式字符串
     * @param safeMode 是否安全模式, 如果是本地用推荐false, 如果是公开推荐true.
     * @return 解析后的结果
     * @deprecated 移动到了HyExpression类
     */
    @Deprecated
    public static String resolve(String raw, boolean safeMode)
    {
        return HyExpression.resolve(raw, safeMode);
    }
}
