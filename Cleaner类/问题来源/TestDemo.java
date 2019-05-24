package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Message {
    public Message() {
        System.out.println("【对象实例化时调用】电闪雷鸣，一代妖孽诞生！");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("【Member被回收】老天爷，要把妖孽收走了！");
        Thread.sleep(Long.MAX_VALUE);
        throw new Exception("老子还想再活500年..."); // 是否抛出异常不影响程序的执行
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message message = new Message();
        message = null; // 当前的对象已经不再拥有指向
        System.gc(); // 强制性进行回收
    }
}
