package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Resource {
    private int number; // 表示要共享操作的数值
    private boolean flag; // 设置一个同步属性

    // flag = true：表示允许进行加法操作，但是不允许执行减法操作
    // flag = false：表示允许进行减法操作，但是不允许执行加法操作
    public synchronized void add() {
        if (this.flag == false) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.number++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("【" + Thread.currentThread().getName() + "】执行加法操作，操作结果为：" + this.number);
        this.flag = false;
        super.notify();
    }

    public synchronized void sub() {
        if (this.flag == true) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.number--;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("【" + Thread.currentThread().getName() + "】执行减法操作，操作结果为：" + this.number);
        this.flag = true;
        super.notify();
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) { // 减法线程
                new Thread(() -> {
                    for (int j = 0; j < 50; j++) { // 处理50次
                        synchronized (resource) {
                            resource.sub(); // 执行减法操作
                        }
                    }
                }, "减法线程 - " + i).start();
            } else { // 加法线程
                new Thread(() -> {
                    for (int j = 0; j < 50; j++) { // 处理50次
                        synchronized (resource) {
                            resource.add(); // 执行加法操作
                        }
                    }
                }, "加法线程 - " + i).start();
            }
        }
    }
}
