package cn.ustb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class InputData { // 模拟一个键盘的输入
    public static Map<String, String> input() {
        Map<String, String> map = new HashMap<String, String>(); // key为属性名称，value为属性内容
        map.put("ename", "小张");
        map.put("job", "办事员");
        return map;
    }
}
