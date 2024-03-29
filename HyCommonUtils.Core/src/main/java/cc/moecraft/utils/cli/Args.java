package cc.moecraft.utils.cli;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

/**
 * 此类由 Hykilpikonna 在 2018/09/16 创建!
 * Created by Hykilpikonna on 2018/09/16!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
@SuppressWarnings("WeakerAccess")
@Data @AllArgsConstructor
public class Args
{
    private ArrayList<String> operations;
    private Map<String, String> options;
}
