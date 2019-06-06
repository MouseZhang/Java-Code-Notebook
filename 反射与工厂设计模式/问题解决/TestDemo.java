package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/6.
 */
interface IMessage {
    public void send(String msg);
}

class NewsPaper implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【报纸报道】：" + msg);
    }
}

class NetMessage implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【网络消息】：" + msg);
    }
}

class Factory {
    private Factory() {
    }

    public static IMessage getInstance(String className) {
        IMessage instance = null;
        try {
            instance = (IMessage) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage message = Factory.getInstance("cn.ustb.demo.NetMessage"); // 获取指定接口实例
        if (message != null) {
            message.send("今天有重大新闻报道～");
        }
    }
}
