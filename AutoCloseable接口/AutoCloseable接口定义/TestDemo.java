package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Message {
    class Connect implements AutoCloseable {
        public boolean build() {
            System.out.println("【Connect】建立网络连接...");
            return true;
        }

        public void close() throws Exception {
            System.out.println("【Connect】关闭连接，释放网络资源...");
        }
    }

    public void send(String msg) {
        try (Connect conn = new Connect()) {
            if (conn.build()) {
                if (true) {
                    throw new RuntimeException("抛出一个异常");
                }
                System.out.println("【消息发送】" + msg);
            }
        } catch (Exception e) {
            System.out.println("产生异常，进行异常处理！");
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message message = new Message();
        message.send("github.com");
    }
}
