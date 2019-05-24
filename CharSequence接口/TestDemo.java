package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */
public class TestDemo {
    public static void main(String[] args) {
        String str = "Hello Every One";
        StringBuffer buffer = new StringBuffer("Hello Kugou");
        StringBuilder builder = new StringBuilder("Hello World");
        print(str);
        print(buffer);
        print(builder);
    }
    public static void print(CharSequence seq) {
        System.out.println(seq);
    }
}
