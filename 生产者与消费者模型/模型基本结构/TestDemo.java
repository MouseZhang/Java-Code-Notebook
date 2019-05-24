package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */
class Message {
    private String title;
    private String info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
                this.message.setTitle("皇马C罗");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.message.setInfo("欧冠之王，最佳射手~");
            } else {
                this.message.setTitle("巴萨梅西");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.message.setInfo("西甲冠军，梅球王~");
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
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.message.getTitle() + " - " + this.message.getInfo());
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