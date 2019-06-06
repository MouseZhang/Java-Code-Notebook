package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public void print() {
        System.out.println("饿汉式单例设计模式");
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.print();
    }
}
