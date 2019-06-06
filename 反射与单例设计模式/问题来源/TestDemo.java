package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
        System.out.println("【Singleton类构造】实例化Singleton类对象");
    }


    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
            }).start();
        }
    }
}
