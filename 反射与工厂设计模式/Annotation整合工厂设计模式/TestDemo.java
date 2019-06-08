package cn.ustb.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MouseZhang on 2019/6/8.
 */

interface IConnect {
    public boolean build();
}

class DatabaseConnect implements IConnect {
    @Override
    public boolean build() {
        System.out.println("【DatabaseConnect】进行数据库资源的连接 ...");
        return true;
    }
}

class CloudConnect implements IConnect {
    @Override
    public boolean build() {
        System.out.println("【CloudConnect】进行云服务器资源的连接 ...");
        return true;
    }
}

@Target({ElementType.TYPE, ElementType.METHOD}) // 当前的Annotation可以出现在类和方法上
@Retention(RetentionPolicy.RUNTIME)
@interface Channel { // 自定义一个Annotation
    public String value(); // 设置一个操作的数据
}

@Channel("cn.ustb.demo.CloudConnect")
class Message {
    private IConnect connect;

    public Message() {
        // 1、获取本类上定义的Annotation对象信息
        Channel channel = super.getClass().getAnnotation(Channel.class);
        // 2、通过Annotation获取类的名称，利用反射加载类的实例
        try {
            this.connect = (IConnect) Class.forName(channel.value()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        if (this.connect.build()) {
            System.out.println("消息发送：" + msg);
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message message = new Message();
        message.send("www.ustb.edu.cn");
    }
}
