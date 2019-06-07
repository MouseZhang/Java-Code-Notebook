package cn.ustb.util.reflect;

import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class ObjectInstanceFactory {
    private ObjectInstanceFactory() {
    }

    /**
     * 根据传入的Class类型获取指定类型的实例化对象，同时可以将传入的属性进行赋值（错误的属性不赋值）
     * @param clazz 要进行实例化对象的简单Java类型
     * @param value 包含有输入数据的Map集合，其中key和value的类型必须是String
     * @param <T>   根据传入的Class类型获取一个具体的实例
     * @return 带有属性内容的简单Java类对象
     */
    public static <T> T create(Class<?> clazz, Map<String, String> value) {
        Object object = null;
        try {
            // 1、在工厂类中调用类中的无参构造方法进行对象实例化处理
            object = clazz.getDeclaredConstructor().newInstance();
            // 2、进行内容的设置，利用反射进行处理
            BeanUtil.setValue(object, value); // 赋值交由其它的类完成
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }
}
