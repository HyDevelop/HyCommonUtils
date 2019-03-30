package cc.moecraft.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * 此类由 Hykilpikonna 在 2018/08/26 创建!
 * Created by Hykilpikonna on 2018/08/26!
 * Github: https://github.com/hykilpikonna
 * Meow!
 *
 * @author Hykilpikonna
 */
public class JsonUtils
{
    /**
     * 把JSONObject转换为JsonArray的一项
     *
     * @param jsonElement Json对象
     * @return JsonArray
     */
    public static JsonArray toJsonArray(JsonElement jsonElement)
    {
        if (jsonElement.isJsonArray()) return jsonElement.getAsJsonArray();

        JsonArray result = new JsonArray();
        result.add(jsonElement);
        return result;
    }
}
