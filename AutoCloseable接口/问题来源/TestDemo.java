package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Message {
    class Connect {
        public boolean build() {
            System.out.println("【Connect】建立网络连接...");
            return true;
        }

        public void close() {
            System.out.println("【Connect】关闭连接，释放网络资源...");
        }
    }

    public void send(String msg) {
        Connect conn = new Connect();
        try {
            if (conn.build()) {
                if (true) {
                    throw new RuntimeException("抛出一个异常");
                }
                System.out.println("【消息发送】" + msg);
            }
        } finally {
            conn.close(); // 最终都执行关闭操作
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message message = new Message();
        message.send("github.com");
    }
}
