package lichen.migration.internal.util;

/**
 * 字符串操作类.
 * @author zhaoyong
 *
 */
public final class StringUtils {
    /**
     * 构造函数.
     */
    private StringUtils() { }

    /**
     * 将指定的字符串数组，通过指定的分隔符连接.
     * @param array 源字符串数组
     * @param sperator 指定的分隔符
     * @return String 拼接后的字符串
     */
    public static String join(final String[] array, final String sperator) {
        StringBuffer sb = new StringBuffer();
        String newSeparator = sperator;
        if (array != null) {
            if (sperator == null) {
                newSeparator = "";
            }
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i < array.length - 1) { //非最后一个元素，需要添加分隔符
                    sb.append(newSeparator);
                }
            }
        }
        return sb.toString();
    }
}
