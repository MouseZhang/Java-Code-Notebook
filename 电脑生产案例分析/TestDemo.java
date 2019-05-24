package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Resource {
    private int number; // 表示要共享操作的数值
    private Computer computer; // 保存电脑类

    public synchronized void create(String brand, double price) {
        if (this.computer != null) { // 生产过了
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.computer = new Computer(brand, price);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" [" + Thread.currentThread().getName() + "] 电脑生产完成：" + this.computer);
        super.notify();
    }

    public synchronized void get() {
        if (this.computer == null) {
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
        System.out.println(" [" + Thread.currentThread().getName() + "] 取走电脑：" + this.computer);
        this.computer = null; // 清空内容
        super.notify();
    }
}

class Computer { // 定义电脑实体类
    private String brand;
    private double price;
    private static int count;

    public Computer(String brand, double price) {
        this.brand = brand;
        this.price = price;
        System.out.println("电脑生产的个数：" + Computer.count++);
    }

    @Override
    public String toString() {
        return "电脑的品牌：" + this.brand + "，价格：" + this.price;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                if (i % 2 == 0) {
                    resource.create("HP电脑", 4999);
                } else {
                    resource.create("MacBook", 18999);
                }
            }
        }, "电脑生产者").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                resource.get();
            }
        }, "电脑消费者").start();
    }
}
