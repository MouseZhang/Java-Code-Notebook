package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */
class Message {
    private String title;
    private String info;
    private boolean flag = true;

    // flag = true：表示可以生产，但是不可以消费
    // flag = false：表示可以消费，但是不可以生产
    public synchronized void set(String title, String info) {
        if (this.flag == false) { // 已经生产过了
            try {
                super.wait(); // 等待消费者唤醒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.info = info;
        this.flag = false;  // 生产过了
        super.notify(); // 唤醒其它等待线程
    }

    public synchronized void get() {
        if (this.flag == true) { // 应该生产
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + " - " + this.info);
        this.flag = true; // 表示恢复生产
        super.notify(); // 唤醒等待线程
    }
}

class Producer implements Runnable {
    private Message message;

    public Producer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                this.message.set("皇马C罗", "欧冠之王，最佳射手~");
            } else {
                this.message.set("巴萨梅西", "西甲冠军，梅球王~");
            }
        }
    }
}

class Consumer implements Runnable {
    private Message message;

    public Consumer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            this.message.get();
        }
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        Message message = new Message();
        Producer pro = new Producer(message);
        Consumer con = new Consumer(message);
        new Thread(pro).start();
        new Thread(con).start();
    }
}