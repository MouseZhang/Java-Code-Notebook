package cn.ustb.util.reflect;

import cn.ustb.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class BeanUtil {
    private BeanUtil() {
    }

    /**
     * 实现字符串数据向指定数据类型的转换
     * @param value 接收输入的字符串数据内容，所有的内容均为String
     * @param field 要转换的目标类型
     * @return 转换的数据结构
     */
    private static Object convertValue(String value, Field field) {
        String fieldName = field.getType().getName();
        if ("java.lang.String".equalsIgnoreCase(fieldName)) {
            return value; // 不转换，直接返回
        }
        if ("int".equalsIgnoreCase(fieldName) || "java.lang.Integer".equalsIgnoreCase(fieldName)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return 0;
            }
        }
        if ("long".equalsIgnoreCase(fieldName) || "java.lang.Long".equalsIgnoreCase(fieldName)) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                return 0;
            }
        }
        if ("double".equalsIgnoreCase(fieldName) || "java.lang.Double".equalsIgnoreCase(fieldName)) {
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
                return 0.0;
            }
        }
        if ("java.util.Date".equalsIgnoreCase(fieldName)) {
            if (value == null || "".equals(value)) {
                return null;
            } else {
                SimpleDateFormat sdf = null;
                if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                } else if (value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                try {
                    return sdf.parse(value);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static void setValue(Object object, Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); // 获取Iterator接口实例
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next(); // 获取每一组数据
            String fieldKey = null;
            Object currentObject = object; // 设置一个当前的操作对象（后面会不断修改其引用）
            try { // 防止某些成员输入错误而导致所有的成员赋值出错
                if (entry.getKey().contains(".")) { // 此时就有可能出现有级联关系
                    // 依据"."进行拆分处理，而后依次判断，如果发现getter方法调用返回的是null，则利用setter实例化
                    String[] fieldSplit = entry.getKey().split("\\."); // 进行拆分处理操作
                    for (int i = 0; i < fieldSplit.length - 1; i++) { // 循环每一个属性
                        Method getMethod = currentObject.getClass().getDeclaredMethod("get" + StringUtil.initcap(fieldSplit[i]));
                        Object tempReturn = getMethod.invoke(currentObject);
                        if (tempReturn == null) { // 当前的对象并未实例化，应该调用setter设置内容
                            Class<?> currentType = currentObject.getClass().getDeclaredField(fieldSplit[i]).getType();
                            Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtil.initcap(fieldSplit[i]), currentType);
                            tempReturn = currentType.getDeclaredConstructor().newInstance();
                            setMethod.invoke(currentObject, tempReturn);
                        }
                        currentObject = tempReturn;
                    }
                    fieldKey = entry.getKey().substring(entry.getKey().lastIndexOf(".") + 1); // 属性
                } else {
                    fieldKey = entry.getKey(); // 直接获取属性名称
                }
                Field field = currentObject.getClass().getDeclaredField(fieldKey);
                // 依据传入的属性名称（key）获取相应的setter方法，并利用Field获取方法参数类型
                Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtil.initcap(fieldKey), field.getType());
                setMethod.invoke(currentObject, convertValue(entry.getValue(), field)); // 反射调用setter方法并设置属性内容
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
