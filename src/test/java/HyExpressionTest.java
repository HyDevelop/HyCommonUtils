import cc.moecraft.utils.HyExpressionResolver;

/**
 * 此类由 Hykilpikonna 在 2018/08/24 创建!
 * Created by Hykilpikonna on 2018/08/24!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class HyExpressionTest
{
    public static void main(String[] args)
    {
        HyExpressionResolver resolver = new HyExpressionResolver(false);

        System.out.println("---------- 没有SafeMode ----------");
        test(resolver);

        resolver.setSafeMode(true);
        System.out.println();

        System.out.println("---------- 有SafeMode ----------");
        test(resolver);
    }

    private static void test(HyExpressionResolver resolver)
    {
        System.out.println(resolver.resolve("今天%rs{很不错,很差,很もえ,很适合写一天代码,很适合玩TH14 EX,很适合删库跑路}"));
        System.out.println(resolver.resolve("您的暑假余额不足, 还剩%ri{-100,-1}天, 请充值"));
        System.out.println(resolver.resolve("抽到了: %rp{SSR卡,3,SR卡,17,R卡,40,N卡,40}"));
        System.out.println(resolver.resolve("%var{a,100}%var{b,50}%cal{(a+b)*b}"));
        System.out.println(resolver.resolve("%var{a,%ri{1,100}}%var{b,%rd{0,1}}%cal{(a+b)*(a+b)*%rd{1,2,4}}"));


        System.out.println(resolver.resolve("%rp{A卡,-1,N卡,-1}"));
        System.out.println(resolver.resolve("%rp{A卡,100,N卡,0}"));

        System.out.println(resolver.resolve("%rs{A的第一行\nA的第二行,B的第一行\nB的第二行}"));

        System.out.println(resolver.resolve("%pref{rc,ps}%rs{A\\tA,%cp{,}B\\tB}"));
    }
}
