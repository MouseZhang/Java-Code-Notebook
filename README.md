# Java Code Notebook

## Java编程学习笔记与案例分析

## 目录

- [1 生产者与消费者模型]()
- [1 生产者与消费者模型](#1 生产者与消费者模型)
- [2 线程加减法案例分析](#2 线程加减法案例分析)
- [3 电脑生产案例分析](#3 电脑生产案例分析)
- [4 问题抢答案例分析](#4 问题抢答案例分析)
- [5 CharSequence接口](#5 CharSequence接口)
- [6 AutoCloseable接口](#6 AutoCloseable接口)
- [7 Cleaner类](#7 Cleaner类)
- [8 ThreadLocal类](#8 ThreadLocal类)
- [9 UUID类](#9 UUID类)
- [10 Optional类](#10 Optional类)
- [11 Base64加密工具类](#11 Base64加密工具类)
- [12 二叉树结构](#12 二叉树结构)
- [13 正则案例分析](#13 正则案例分析)
- [14 二叉树案例分析](#14 二叉树案例分析)
- [15 HTML匹配拆分](#15 HTML匹配拆分)
- [16 国际化案例分析](#16 国际化案例分析)
- [17 比较器案例分析](#17 比较器案例分析)

---

## 1 生产者与消费者模型

### 1.1 模型基本结构

> 生产者和消费者模型程序中最大的特点在于，生产者是一个线程，消费者也是一个线程，并且两个线程之间没有任何的直接联系，生产者负责数据的生产，而消费者负责数据的获取，每当生产者生产完成一个数据以后，消费者就要立即取走数据。

待放入类图。。。。

> 此时的生产者希望可以生产出两类数据：
>
> - title = 皇马C罗，content = 欧冠之王，最佳射手~
> - title = 巴萨梅西，content = 西甲冠军，梅球王~

**范例：**基本模型

```java
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
```

**程序执行结果：**

```
巴萨梅西 - 欧冠之王，最佳射手~
皇马C罗 - 西甲冠军，梅球王~
巴萨梅西 - 欧冠之王，最佳射手~
皇马C罗 - 西甲冠军，梅球王~
巴萨梅西 - 欧冠之王，最佳射手~
皇马C罗 - 西甲冠军，梅球王~
巴萨梅西 - 欧冠之王，最佳射手~
皇马C罗 - 西甲冠军，梅球王~
... ...
```

> 此时的代码已经可以实现交互了，但是通过最终的执行效果可以发现存在以下两个问题：
>
> - 问题一：数据发生了错位
> - 问题二：数据并没有按照要求，生产一个取走一个

### 1.2 解决数据同步问题

> 在多线程进行资源访问的时候一定需要进行同步处理，此时的问题需要通过同步的形式来进行访问。实际上当前最大的问题在于：当数据生产到一半的时候就有可能被消费者把数据取走。

**范例：**修改程序结构追加同步处理

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */
class Message {
    private String title;
    private String info;

    public synchronized void set(String title, String info) {
        this.title = title;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.info = info;
    }

    public synchronized void get() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + " - " + this.info);
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
```

**程序执行结果：**

```
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
皇马C罗 - 欧冠之王，最佳射手~
... ...
```

> 通过修改后代码的执行效果可以发现，的确解决了当前数据不同步的问题，但是发现重复取出的问题更严重了。

### 1.3 解决数据重复问题

> 正常的开发流程里应该是生产出一个数据之后就取走一个数据，但是此时的结果会发现并没有按照预计的模式完成，因为同步造成了问题的严重性。如果想要解决此类问题就必须引入等待与唤醒机制，而等待与唤醒机制的操作是在Object类中定义的。

|  序号  |                   方法名称                   |         方法描述         |
| :--: | :--------------------------------------: | :------------------: |
|  1   | public final void wait() throws InterruptedException |        等待，死等         |
|  2   | public final void wait(long timeout) throws InterruptedException | 等待到若干毫秒之后如果还未唤醒，自动结束 |
|  3   | public final void wait(long timeout, int nanos) throws InterruptedException |    等待，并设置等待到超时时间     |
|  4   |        public final void notify()        |      唤醒第一个等待线程       |
|  5   |      public final void notifyAll()       |   唤醒所有的线程，优先级高的先执行   |

**范例：**利用等待与唤醒机制实现多线程的控制

```java
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
```

**程序执行结果：**

```
皇马C罗 - 欧冠之王，最佳射手~
巴萨梅西 - 西甲冠军，梅球王~
皇马C罗 - 欧冠之王，最佳射手~
巴萨梅西 - 西甲冠军，梅球王~
皇马C罗 - 欧冠之王，最佳射手~
巴萨梅西 - 西甲冠军，梅球王~
皇马C罗 - 欧冠之王，最佳射手~
巴萨梅西 - 西甲冠军，梅球王~
... ...
```

此时的数据没有任何错误，也是按照预计的要求来实现的。

---

## 2 线程加减法案例分析

### 2.1 案例要求

> 设计4个线程对象，两个线程执行减法操作，两个线程执行加法操作。

### 2.2 案例分析

> 对于本程序来讲最终的目的还是多个线程访问同一资源，但是区别在于，操作者变为多份，应该使用生产者与消费者模型。

**范例：**生产者与消费者模型

```

```

