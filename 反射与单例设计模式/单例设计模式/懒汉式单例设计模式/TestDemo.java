package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public void print() {
        System.out.println("懒汉式单例设计模式");
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.print();
    }
}
