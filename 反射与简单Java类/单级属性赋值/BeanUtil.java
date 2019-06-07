package cn.ustb.util.reflect;

import cn.ustb.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class BeanUtil {
    private BeanUtil() {
    }

    public static void setValue(Object object, Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); // 获取Iterator接口实例
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next(); // 获取每一组数据
            try { // 防止某些成员输入错误而导致所有的成员赋值出错
                Field field = object.getClass().getDeclaredField(entry.getKey());
                // 依据传入的属性名称（key）获取相应的setter方法，并利用Field获取方法参数类型
                Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtil.initcap(entry.getKey()), field.getType());
                setMethod.invoke(object, entry.getValue()); // 反射调用setter方法并设置属性内容
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
