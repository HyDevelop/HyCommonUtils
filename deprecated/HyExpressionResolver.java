

/**
 * 获取格式标签
 * @param original 源字符串
 * @param pattern 格式
 * @return 标签数组
 */
private static ArrayList<String> getTags(String original, Pattern pattern)
        {
        ArrayList<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(original);

        while (matcher.find()) result.add(matcher.group());
        return result;
        }

private static ArrayList<String[]> getSplitTags(String original, Pattern pattern)
        {
        ArrayList<String> tags = getTags(original, pattern);
        ArrayList<String[]> result = new ArrayList<>();
        tags.forEach(tag -> result.add(tag.split(",")));

        return result;
        }

@Data @AllArgsConstructor
private static class OperationData implements Comparable<OperationData>
{
    private int start;
    private Operation operation;
    private String value;

    @Override
    public int compareTo(OperationData o)
    {
        return start - o.start;
    }
}