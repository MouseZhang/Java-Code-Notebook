package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/6.
 */
interface IMessage {
    public void send(String msg);
}

class NewsPaper implements IMessage {
    public void send(String msg) {
        System.out.println("【报纸报道】：" + msg);
    }
}

class Factory {
    private Factory() {
    }

    public static IMessage getInstance(String className) {
        if ("newspaper".equalsIgnoreCase(className)) {
            return new NewsPaper();
        }
        return null;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage message = Factory.getInstance("NewsPaper"); // 获取指定接口实例
        message.send("今天有重大新闻报道～");
    }
}
