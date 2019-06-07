package cn.ustb.util;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class StringUtil {
    private StringUtil() {
    }

    public static String initcap(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
