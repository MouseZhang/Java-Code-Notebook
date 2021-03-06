# Java Code Notebook

## Java编程学习笔记与案例分析

## 目录

- [1 生产者与消费者模型](#生产者与消费者模型)
- [2 线程加减法案例分析](#线程加减法案例分析)
- [3 电脑生产案例分析](#电脑生产案例分析)
- [4 问题抢答案例分析](#问题抢答案例分析)
- [5 CharSequence接口](#CharSequence接口)
- [6 AutoCloseable接口](#AutoCloseable接口)
- [7 Cleaner类](#Cleaner类)
- [8 ThreadLocal类](#ThreadLocal类)
- [9 UUID类](#UUID类)
- [10 Optional类](#Optional类)
- [11 Base64加密工具类](#Base64加密工具类)
- [12 二叉树结构](#二叉树结构)
- [13 正则案例分析](#正则案例分析)
- [14 二叉树案例分析](#二叉树案例分析)
- [15 HTML匹配拆分](#HTML匹配拆分)
- [16 国际化案例分析](#国际化案例分析)
- [17 比较器案例分析](#比较器案例分析)
- [18 奇偶数统计案例分析](#奇偶数统计案例分析)
- [19 数据排序案例分析](#数据排序案例分析)  
- [20 用户登录案例分析](#用户登录案例分析)
- [21 选举投票案例分析](#选举投票案例分析)
- [22 反射与工厂设计模式](#反射与工厂设计模式)
- [23 反射与单例设计模式](#反射与单例设计模式)
- [24 反射与代理设计模式](#反射与代理设计模式)
- [25 反射与简单Java类](#反射与简单Java类)

---

## 1 生产者与消费者模型

### 1.1 模型基本结构

> 生产者和消费者模型程序中最大的特点在于，生产者是一个线程，消费者也是一个线程，并且两个线程之间没有任何的直接联系，生产者负责数据的生产，而消费者负责数据的获取，每当生产者生产完成一个数据以后，消费者就要立即取走数据。

![生产者与消费者类图](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/生产者与消费者模型/模型基本结构/ProducerAndConsumer.png)

> 此时的生产者希望可以生产出两类数据：
>
> - title = 皇马C罗，content = 欧冠之王，最佳射手~
> - title = 巴萨梅西，content = 西甲冠军，梅球王~

**范例：** 基本模型

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
> - 问题一：数据发生了错位；
> - 问题二：数据并没有按照要求，生产一个取走一个。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/生产者与消费者模型/模型基本结构/TestDemo.java)

### 1.2 解决数据同步问题

> 在多线程进行资源访问的时候一定需要进行同步处理，此时的问题需要通过同步的形式来进行访问。实际上当前最大的问题在于：当数据生产到一半的时候就有可能被消费者把数据取走。

**范例：** 修改程序结构追加同步处理

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

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/生产者与消费者模型/解决数据同步问题/TestDemo.java)

### 1.3 解决数据重复问题

> 正常的开发流程里应该是生产出一个数据之后就取走一个数据，但是此时的结果会发现并没有按照预计的模式完成，因为同步造成了问题的严重性。如果想要解决此类问题就必须引入等待与唤醒机制，而等待与唤醒机制的操作是在Object类中定义的。

|  No  |                           方法名称                           |                 方法描述                 |
| :--: | :----------------------------------------------------------: | :--------------------------------------: |
|  1   |     public final void wait() throws InterruptedException     |                等待，死等                |
|  2   | public final void wait(long timeout) throws InterruptedException | 等待到若干毫秒之后如果还未唤醒，自动结束 |
|  3   | public final void wait(long timeout, int nanos) throws InterruptedException |        等待，并设置等待到超时时间        |
|  4   |                  public final void notify()                  |            唤醒第一个等待线程            |
|  5   |                public final void notifyAll()                 |     唤醒所有的线程，优先级高的先执行     |

**范例：** 利用等待与唤醒机制实现多线程的控制

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

> 此时的数据没有任何错误，也是按照预计的要求来实现的。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/生产者与消费者模型/解决数据重复问题/TestDemo.java)

---

## 2 线程加减法案例分析

### 2.1 案例要求

> 设计4个线程对象，两个线程执行减法操作，两个线程执行加法操作。

### 2.2 案例分析与实现

> 对于本程序来讲最终的目的还是多个线程访问同一资源，但是区别在于，操作者变为多份，应该使用生产者与消费者模型。

**范例：** 生产者与消费者模型

```java
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

```

**程序执行结果：**

```
【减法线程 - 0】执行减法操作，操作结果为：-1
【加法线程 - 5】执行加法操作，操作结果为：0
【减法线程 - 4】执行减法操作，操作结果为：-1
【加法线程 - 3】执行加法操作，操作结果为：0
【减法线程 - 2】执行减法操作，操作结果为：-1
【加法线程 - 1】执行加法操作，操作结果为：0
【减法线程 - 2】执行减法操作，操作结果为：-1
【加法线程 - 1】执行加法操作，操作结果为：0
【减法线程 - 2】执行减法操作，操作结果为：-1
 ... ...
```

> 此时的程序针对于某一个操作会有多个并行的线程出现，所以还需要考虑这多个线程的同步处理操作。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/线程加减法案例分析/TestDemo.java)

---

## 3 电脑生产案例分析

### 3.1 案例要求

> 设计一个生产电脑和搬运电脑的类，要求生产出一台电脑就搬走一台电脑，如果没有新的电脑生产出来，则搬运工要等待新电脑产出；如果生产的电脑没有被搬走，则要等待电脑搬走之后再生产，并统计出生产的电脑数量。

### 3.2 案例分析与实现

> 本程序是一个典型的生产者与消费者的程序，但是所生产和消费的是一个完整电脑信息。

**范例：** 生产者与消费者模型

```java
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
        System.out.println("【" + Thread.currentThread().getName() + "】电脑生产完成：" + this.computer);
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
        System.out.println("【" + Thread.currentThread().getName() + "】取走电脑：" + this.computer);
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
```

**程序执行结果：**

```
电脑生产的个数：0
【电脑生产者】电脑生产完成：电脑的品牌：HP电脑，价格：4999.0
【电脑消费者】取走电脑：电脑的品牌：HP电脑，价格：4999.0
电脑生产的个数：1
【电脑生产者】电脑生产完成：电脑的品牌：MacBook，价格：18999.0
【电脑消费者】取走电脑：电脑的品牌：MacBook，价格：18999.0
电脑生产的个数：2
【电脑生产者】电脑生产完成：电脑的品牌：HP电脑，价格：4999.0
【电脑消费者】取走电脑：电脑的品牌：HP电脑，价格：4999.0
电脑生产的个数：3
【电脑生产者】电脑生产完成：电脑的品牌：MacBook，价格：18999.0
【电脑消费者】取走电脑：电脑的品牌：MacBook，价格：18999.0
 ... ...
```

> 此时Computer只是作为一个数据的载体存在，而所有的同步处理操作全部由Resource负责。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/电脑生产案例分析/TestDemo.java)

---

## 4 问题抢答案例分析

### 4.1 案例要求

> 实现一个竞拍抢答程序：要求设置3个抢答者（3个线程），然后同时发出抢答指令，抢答成功者给出抢答成功提示，未抢答者给出失败提示。

### 4.2 案例分析与实现

> 本程序需要设置有一个操作结果的返回值，所以既然需要线程获取结果，最好使用Callable接口来实现抢答过程。

**范例：** 问题抢答程序设计

```java
package cn.ustb.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class AnswerThread implements Callable<String> {
    private boolean flag; // 描述抢答结果

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        synchronized (this) {
            String result = null;
            if (this.flag == false) { // 表示可以抢答
                this.flag = true;
                result = "【" + Thread.currentThread().getName() + "】抢答成功！";
            } else {
                result = "【" + Thread.currentThread().getName() + "】抢答失败！";
            }
            return result;
        }
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception{
        AnswerThread thread = new AnswerThread();
        FutureTask<String> taskA = new FutureTask<String>(thread);
        FutureTask<String> taskB = new FutureTask<String>(thread);
        FutureTask<String> taskC = new FutureTask<String>(thread);
        new Thread(taskA, "抢答者-A").start();
        new Thread(taskB, "抢答者-B").start();
        new Thread(taskC, "抢答者-C").start();
        System.out.println(taskA.get());
        System.out.println(taskB.get());
        System.out.println(taskC.get());
    }
}
```

**程序执行结果：**

```
【抢答者-A】抢答失败！
【抢答者-B】抢答成功！
【抢答者-C】抢答失败！
```

> 本程序依然属于同一资源的数据共享操作。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/问题抢答案例分析/TestDemo.java)

---

## 5 CharSequence接口

### 5.1 CharSequence接口定义

> **CharSequence**是在JDK1.4时定义的接口，其描述的是一个字符序列标准操作接口。实际上，字符序列里面包含：String、StringBuffer、StringBuilder，下面来观察一下这三个类的定义形式：

| String类(JDK1.0)定义：                                       | StringBuffer类(JDK1.0)定义：                                 | StringBuilder类(JDK1.5)定义：                                |
| ------------------------------------------------------------ | :----------------------------------------------------------- | ------------------------------------------------------------ |
| public final class String<br/> extends Object <br/>implements Serializable, Comparable<String>, CharSequence | public final class StringBuffer extends Object<br/>implements Serializable, Comparable<StringBuffer>, CharSequence | public final class StringBuilder<br/>extends Object<br/>implements Serializable, Comparable<StringBuilder>, CharSequence |

> 可以发现这三个类实际上都是CharSequence接口的子类，而CharSequence接口中定义有如下几个方法：

|  No  |           方法名称            |              方法描述              |
| :--: | :---------------------------: | :--------------------------------: |
|  1   | public char charAt(int index) |          根据索引获取字符          |
|  2   |      public int length()      |           获取序列的长度           |
|  3   |   public IntStream chars()    | 进行数据流分析（性能比使用循环高） |

> StringBuffer与StringBuilder本质上都是相同的操作功能，但是两者有如下的不同：
>
> - StringBuffer是在JDK1.0的时候设计的，而StringBuilder是在JDK1.5时候设计的；
> - StringBuffer类中的方法全部使用了同步处理，而StringBuilder类使用了异步处理；
> - StringBuffer性能较低，但是属于线程安全的操作，而StringBuilder性能较高，属于非线程安全操作。

![CharSequence](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/CharSequence接口/CharSequence.png)

> 在以后开发中如果见到了CharSequence就需要向里面传递任意的一个字符串数据。

**范例：** 为CharSequence实例化

```java
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
```

**程序执行结果：**

```
Hello Every One
Hello Kugou
Hello World
```

> 以后只要发现有参数上使用了CharSequence最简单的做法就是传递一个字符串。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/CharSequence接口/TestDemo.java)

---

## 6 AutoCloseable接口

### 6.1 问题来源

> 在程序里面有可能会进行一些外部资源的操作，而所有外部资源操作完毕之后往往都需要进行手工的关闭处理，现在以消息的发送处理为例。

**范例：** 观察传统代码的问题

```java
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

```

**程序执行结果：**

```
【Connect】建立网络连接...
Exception in thread "main" 【Connect】关闭连接，释放网络资源...
java.lang.RuntimeException: 抛出一个异常
	at cn.ustb.demo.Message.send(TestDemo.java:24)
	at cn.ustb.demo.TestDemo.main(TestDemo.java:37)
```

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/AutoCloseable接口/问题来源/TestDemo.java)

### 6.2 AutoCloseable接口定义

> 此时的代码每一次释放资源的同时都必须明确地调用close()方法，这样的处理逻辑过于繁琐。为了简化操作，在这个基础上，从JDK1.7开始追加了一个新的**AutoCloseable**接口，用于自动关闭：

```java
public interface AutoCloseable {
    public void close() throws Exception;
}
```

> 要想使用自动关闭处理，必须严格遵照语法要求，也就是说需要修改已知的异常处理结构，格式为：

```java
try (AutoCloseable接口子类 对象 = new 类()) {
    // 编写相应的处理方法
} catch (Exception e) {}
```

**范例：** 实现自动关闭处理

```java
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
```

**程序执行结果：**

```
【Connect】建立网络连接...
【Connect】关闭连接，释放网络资源...
产生异常，进行异常处理！
```

> 使用AutoCloseable可以实现自动地资源释放处理操作，在以后的Java IO、数据库编程和网络编程也会经常使用此接口。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/AutoCloseable接口/接口定义/TestDemo.java)

---

## 7 Cleaner类

### 7.1 问题来源

> 在任意一个类进行对象创建的时候一定会调用构造方法，利用构造方法进行对象属性的初始化操作，但是所有类对象在不使用的时候也需要进行一个处理，那么早期的处理使用的都是Object中的finalize()方法。

**范例：** 传统对象回收处理

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Member {
    public Member() {
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
        Member member = new Member();
        member = null; // 当前的对象已经不再拥有指向
        System.gc(); // 强制性进行回收
    }
}
```

**程序执行结果：**

```
【对象实例化时调用】电闪雷鸣，一代妖孽诞生！
【Member被回收】老天爷，要把妖孽收走了！
```

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/Cleaner类/问题来源/TestDemo.java)

### 7.2 Cleaner类定义

> 在Object类中定义的finalize()方法，最为主要的功能是进行了对象回收前的处理操作，所以在这些操作里面传统的做法是进行方法的覆写，同时不管代码之中出现有任何错误，实际上都不会影响到程序的执行，但是传统的这种做法里面，会由于对象本身的回收或者产生死锁问题，从而导致对象的回收失败，所以finalize()方法在JDK1.9之后就被废除了。

**范例：** 自定义回收处理

```java
package cn.ustb.demo;

import java.lang.ref.Cleaner;

/**
 * Created by MouseZhang on 2019/5/24.
 */

class Member implements Runnable {
    public Member() {
        System.out.println("【对象实例化时调用】电闪雷鸣，一代妖孽诞生！");
    }

    @Override
    public void run() {
        System.out.println("【Member被回收】老天爷，要把妖孽收走了！");
    }
}

class MemberCleaner implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create(); // 创建一个回收对象
    private Cleaner.Cleanable cleanable; // 可以被回收的对象

    public MemberCleaner(Member member) { // 处理要回收的对象
        this.cleanable = cleaner.register(this, member); // 注册一个可回收对象
    }

    @Override
    public void close() throws Exception { // 释放资源
        this.cleanable.clean(); // 回收对象
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Member member = new Member();
        System.gc(); // 强制性进行回收
        try (MemberCleaner mc = new MemberCleaner(member)) { // 进行对象回收的处理
            // 如果有需求则可以进行其他处理操作
        } catch (Exception e) {
        }
    }
}
```

**程序执行结果：**

```
【对象实例化时调用】电闪雷鸣，一代妖孽诞生！
【Member被回收】老天爷，要把妖孽收走了！
```

> 以上操作是新一代的对象回收处理机制，它需要利用多线程的形式来实现回收处理，这样可以避免造成死锁或延误导致的对象回收异常问题。

——生命周期—待画【【【【】】】】

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/Cleaner类/Cleaner类定义/TestDemo.java)

---

## 18 奇偶数统计案例分析

### 18.1 案例要求

> 编写程序，当程序运行后，根据屏幕提示输入一个数字字符串，输入后统计有多少个奇数数字和偶数数字。

### 18.2 案例分析与实现

——类图【【【【【】】】】】】

> 在进行统计的时候肯定要将输入的字符串进行拆分处理，但是面对拆分必须清楚要拆分的类型：
>
> - 可以将字符串利用split()方法拆分为字符串的对象数组，将字符串数据的内容取出，然后利用Integer转型后除2；
> - 可以将字符串利用toCharArray()方法拆分为字符数组，减少垃圾空间，字符直接判断，避免转型。

**范例：** 定义数据的输入标准

```java
package cn.ustb.service;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public interface IInputData {
    /**
     * 实现字符串数据的输入
     *
     * @param prompt 输入时的提示信息
     * @return 返回一个输入的字符串（可以为空）
     */
    public String getString(String prompt);

    /**
     * 实现字符串数据的输入
     *
     * @param prompt 输入时的提示信息
     * @return 返回一个不为空的字符串
     */
    public String getStringNotNull(String prompt);
}
```

**范例：** 定义键盘数据输入类

```java
package cn.ustb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class KeyboardInputData implements IInputData {
    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getString(String prompt) {
        System.out.println(prompt);
        String value = null;
        try {
            value = INPUT.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public String getStringNotNull(String prompt) {
        boolean flag = true;
        String value = null;
        while (flag) {
            value = this.getString(prompt);
            if (!(value == null || "".equals(value))) {
                flag = false;
            } else {
                System.err.println("输入的数据不允许为空！");
            }
        }
        return value;
    }
}
```

**范例：** 定义一个专属的工厂类，隐藏实现子类

```java
package cn.ustb.factory;

import cn.ustb.service.IInputData;
import cn.ustb.service.KeyboardInputData;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Factory {
    private Factory() {
    }

    public static IInputData getInputDataInstance() {
        return new KeyboardInputData();
    }
}
```

**范例：** 定义一个统计的操作类

```java
package cn.ustb.service;

import cn.ustb.factory.Factory;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Stat {
    private int odd; // 保存奇数个数
    private int even; // 保存偶数个数
    private String inputData; // 保存输入的字符串
    private IInputData input = Factory.getInputDataInstance();

    public void input() { // 数据输入操作
        boolean flag = true; // 循环标记
        while (flag) {
            String value = input.getStringNotNull("请输入一串数字：");
            if (!value.matches("\\d+")) {
                System.err.println("输入的内容必须是一串数字，请重新输入！");
            } else {
                this.inputData = value;
                flag = false;
            }
        }
    }

    public void cal() { // 进行最终的统计计算
        if (this.inputData != null) { // 已经输入过数据
            char[] data = this.inputData.toCharArray();
            for (int i = 0; i < data.length; i++) {
                if (data[i] == '0' || data[i] == '2' || data[i] == '4' || data[i] == '6' || data[i] == '8') {
                    this.even++;
                } else {
                    this.odd++;
                }
            }
        }
    }

    public int getOdd() {
        return this.odd;
    }

    public int getEven() {
        return this.even;
    }
}
```

**范例：** 编写测试代码进行功能功能调用

```java
package cn.ustb.demo;

import cn.ustb.service.Stat;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Stat stat = new Stat();
        stat.input(); // 输入数据
        stat.cal(); // 统计计算
        System.out.println("奇书个数：" + stat.getOdd() + "、偶数个数：" + stat.getEven());
    }
}
```

**程序执行结果：**

```
请输入一串数字：
213233244531253665436577
奇书个数：14、偶数个数：10
```

> 面向对象的解决方案绝对不是直接在主方法上编写代码，而是要有合理的类结构设计。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/奇偶数统计案例分析)

---

## 19 数据排序案例分析

### 19.1 案例要求

> 从键盘输入以下的数据：“TOM:89|JERRY:90|TONY:95”，数据格式为“姓名:成绩|姓名:成绩|姓名:成绩”，对输入的内容按成绩进行排序，并将排序结果按照成绩由高到低排序。

### 19.2 案例分析与实现

——类图【【【【【】】】】】】

**范例：** 创建学生信息类，并实现Comparable接口

```java
package cn.ustb.co;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Student implements Comparable<Student> {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        if (this.score < o.score) {
            return 1;
        } else if (this.score > o.score) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "姓名：" + this.name + "、成绩：" + this.score;
    }
}
```

 **范例：** 定义正则验证器

```java
package cn.ustb.validate.impl;

import cn.ustb.validate.IValidator;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class RegexValidator implements IValidator { // 正则验证器
    private String regex;

    public RegexValidator(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean check(String value) {
        if (value == null || "".equals(value)) {
            return false;
        }
        return value.matches(this.regex);
    }
}
```

**范例：** 工厂类中注册新的接口实例获取操作

```java
package cn.ustb.factory;

import cn.ustb.validate.IValidator;
import cn.ustb.validate.impl.RegexValidator;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Factory {
    private Factory() {}
    public static IValidator getValidatorInstance(String regex) {
        return new RegexValidator(regex);
    }
}
```

**范例：** 定义程序的主体类

```java
package cn.ustb.service;

import cn.ustb.co.Student;
import cn.ustb.factory.Factory;
import cn.ustb.validate.IValidator;

import java.util.Arrays;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class MainService { //
    private IInputData input = Factory.getInputDataInstance();
    private IValidator validator = Factory.getValidatorInstance("([a-zA-Z]+:\\d{1,3}(\\.\\d{1,2})?\\|?)+");
    private String inputData;
    private Student[] students;

    public void input() {
        boolean flag = true;
        while (flag) {
            String value = this.input.getStringNotNull("请输入统计记录（格式：\"姓名:成绩|姓名:成绩|...\"）：");
            if (this.validator.check(value)) { // 验证通过
                this.inputData = value;
                this.handleData(); // 进行数据处理
                flag = false; // 结束循环
            } else {
                System.err.println("数据格式输入错误，请重新输入！");
            }
        }
    }

    private void handleData() {
        String[] result = this.inputData.split("\\|");
        this.students = new Student[result.length];
        for (int i = 0; i < students.length; i++) {
            String[] temp = result[i].split(":");
            students[i] = new Student(temp[0], Double.parseDouble(temp[1]));
        }
        Arrays.sort(this.students);
    }

    public Student[] getStudents() {
        return this.students;
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.service.MainService;

import java.util.Arrays;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class TestDemo {
    public static void main(String[] args) throws Exception { // TOM:89|JERRY:90|TONY:95
        MainService main = new MainService();
        main.input(); // 接收输入数据
        System.out.println(Arrays.toString(main.getStudents()));
    }
}
```

**程序执行结果：**

```
请输入统计记录（格式："姓名:成绩|姓名:成绩|..."）：
TOM:89|JERRY:90|TONY:95
[姓名：TONY、成绩：95.0, 姓名：JERRY、成绩：90.0, 姓名：TOM、成绩：89.0]
```

> 功能类结构的设计是整个项目的关键，客户端只需要保持简单调用即可。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/数据排序案例分析)

----

## 20 用户登录案例分析

### 20.1 案例要求

> 完成系统登录程序，从命令行输入用户名和密码。如果没有输入用户名和密码，则提示用户输入用户名和密码；如果输入了用户名但是没有输入密码，则提示用户输入密码；然后判断用户名是否为user，密码是否是12345。正确则提示登录成功，错误则显示登录失败的信息，要求用户再次输入用户名和密码，连续3次输入错误后系统退出。

### 20.2 案例分析与实现

——类图【【【【【】】】】】】

**范例：** 增加一个登录验证处理程序

```java
package cn.ustb.service;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public interface ILoginService {
    public boolean login();
}
```

**范例：** 定义登录为固定信息时的子类

```java
package cn.ustb.service.impl;

import cn.ustb.service.ILoginService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class ValueLoginService implements ILoginService {
    private String username;
    private String password;

    public ValueLoginService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login() {
        return "user".equals(this.username) && "12345".equals(this.password);
    }
}
```

**范例：** 追加一个新的工厂类配置

```java
package cn.ustb.factory;

import cn.ustb.service.ILoginService;
import cn.ustb.service.impl.ValueLoginService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class Factory {
    private Factory() {
    }

    public static ILoginService getLoginServiceInstance(String username, String password) {
        return new ValueLoginService(username, password);
    }
}
```

**范例：** 创建主服务类

```java
package cn.ustb.service;

import cn.ustb.factory.Factory;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class MainService {
    private IInputData input = Factory.getInputDataInstance();
    private String username;
    private String password;
    private static int count = 0; // 记录登录次数

    public void input() { // 实现数据的输入处理
        String content = this.input.getStringNotNull("请输入登录用户名：");
        if (content.contains("/")) { // 如果输入用户名和密码
            String[] result = content.split("/");
            if (!content.endsWith("/")) {
                if (result.length == 2) { // 长度正确
                    username = result[0];
                    password = result[1];
                }
            } else {
                this.username = result[0];
                this.password = this.input.getStringNotNull("请输入登录密码：");
            }
        } else {  // 只输入了用户名
            this.username = content;
            this.password = this.input.getStringNotNull("请输入登录密码：");
        }
    }

    public String login() {
        ILoginService loginService = Factory.getLoginServiceInstance(this.username, this.password);
        if (count >= 2) {
            return "登录尝试次数过多，无法登录，程序退出！";
        }
        if (loginService.login()) {
            return "登录成功，欢迎" + this.username + "光临！";
        } else {
            System.err.println("登录失败，错误的用户名和密码！");
            count++;
            this.input(); // 重新进行登录输入
            return this.login();
        }
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.service.MainService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class TestDemo {
    public static void main(String[] args) throws Exception {
        MainService main = new MainService();
        main.input(); // 获取输入信息
        System.out.println(main.login());
    }
}
```

**程序执行结果：**

```
请输入登录用户名：
user/12345
登录成功，欢迎user光临！
```

> 该程序设计的登录验证结构除了可以使用固定信息数值验证以外，实际上也可以实现基于数据库的登录验证。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/用户登录案例分析)

---

## 21 选举投票案例分析

### 21.1 案例要求

> 一个班采用民主投票方法推选班长，班长候选人共4位，每个人姓名及代号分别是“张三  1；李四  2；王五  3；赵六  4”。程序操作员将每张选票上所填的代号（1、2、3或4）循环输入电脑，输入数字0结束输入，然后将所有候选人的得票情况显示出来，并显示最终当选者的信息，具体要求如下：
>
> - 要求用面向对象方法，编写学生类Student，将候选人姓名、代号和票数保存到学生类中，并实现相应的getter 和 setter方法；
> - 输入数据前，显示出各位候选人的代号及姓名（提示，建立一个候选人类型数组）；
> - 循环执行接收键盘输入的班长候选人代号，直到输入的数字为0，结束选票的输入工作；
> - 在接收每次输入的选票后要求验证该选票是否有效，即如果输入的数不是0、1、2、3、4这5个数字之一，或者输入的是一串字母，应显示出错误提示信息“此选票无效，请输入正确的候选人代号！”，并继续等待输入；
> - 输入结束后显示所有候选人的得票情况；
> - 输出最终当选者的相关信息，参考样例如下所示。

```
1：张三【0票】
2：李四【0票】
3：王五【0票】
4：赵六【0票】
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：2
请输入班长候选人代号（数字0结束）：3
请输入班长候选人代号（数字0结束）：4
请输入班长候选人代号（数字0结束）：5
此选票无效，请输入正确的候选人代号！
请输入班长候选人代号（数字0结束）：hello
此选票无效，请输入正确的候选人代号！
请输入班长候选人代号（数字0结束）：0
1：张三【4票】
2：李四【1票】
3：王五【1票】
4：赵六【1票】
投票最终结果：张三同学，最后以4票当选班长！
```

### 21.2 案例分析与实现

——类图【【【【【】】】】】】

**范例：** 定义候选人信息

```java
package cn.ustb.co;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class Student implements Comparable<Student> {
    private int stuNo;
    private String name;
    private int vote;

    public Student(int stuNo, String name, int vote) {
        this.setStuNo(stuNo);
        this.setName(name);
        this.setVote(vote);
    }

    public int getStuNo() {
        return stuNo;
    }

    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public int compareTo(Student o) {
        if (this.vote < o.vote) {
            return 1;
        } else if (this.vote > o.vote) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.getStuNo() + "：" + this.getName() + "【" + this.getVote() + "】";
    }
}
```

**范例：** 修改IInputData接口的功能，追加数字的输入功能

```java
public int getIntNotNull(String prompt) {
  boolean flag = true;
  int number = 0;
  while (flag) {
    String value = this.getString(prompt); // 接收数据的输入
    if (!(value == null || "".equals(value))) {
      if (value.matches("\\d+")) { // 判断是数字
        number = Integer.parseInt(value);
        flag = false;
      } else {
        System.err.println("此选票无效，请输入正确的候选人代号！");
      }
    } else {
      System.err.println("此选票无效，请输入正确的候选人代号！");
    }
  }
  return number;
}
```

**范例：** 定义投票管理类

```java
package cn.ustb.service;

import cn.ustb.co.Student;
import cn.ustb.factory.Factory;

import java.util.Arrays;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class MainService {
    private Student[] students = {new Student(1, "张三", 0), new Student(2, "李四", 0),
            new Student(3, "王五", 0), new Student(4, "赵六", 0)};
    private IInputData input = Factory.getInputDataInstance();
    private boolean flag = true;

    public MainService() {
        this.show();
        while (flag) {
            this.vote();
        }
        this.show();
        this.result();
    }

    public void show() { // 显示所有候选人信息
        for (Student stu : this.students) {
            System.out.println(stu);
        }
    }

    public void vote() { // 开始投票
        int num = input.getIntNotNull("请输入班长候选人代号（数字0结束）：");
        switch (num) {
            case 0: {
                this.flag = false;
                break;
            }
            case 1: {
                this.students[0].setVote(this.students[0].getVote() + 1);
                break;
            }
            case 2: {
                this.students[1].setVote(this.students[1].getVote() + 1);
                break;
            }
            case 3: {
                this.students[2].setVote(this.students[2].getVote() + 1);
                break;
            }
            case 4: {
                this.students[3].setVote(this.students[3].getVote() + 1);
                break;
            }
            default: {
                System.out.println("此选票无效，请输入正确的候选人代号！");
            }
        }
    }

    public void result() {
        Arrays.sort(this.students);
        System.out.println("投票最终结果：" + this.students[0].getName() + "同学，最后以" + this.students[0].getVote() + "票当选班长！");
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.service.MainService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class TestDemo {
    public static void main(String[] args) throws Exception {
        new MainService();
    }
}
```

**程序执行结果：**

```
1：张三【0】
2：李四【0】
3：王五【0】
4：赵六【0】
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：2
请输入班长候选人代号（数字0结束）：2
请输入班长候选人代号（数字0结束）：3
请输入班长候选人代号（数字0结束）：4
请输入班长候选人代号（数字0结束）：1
请输入班长候选人代号（数字0结束）：0
1：张三【4】
2：李四【2】
3：王五【1】
4：赵六【1】
投票最终结果：张三同学，最后以4票当选班长！
```

> 在整体实现的代码里面，所有的输入数据基本上都以字符串为主，在实际的开发中，字符串也是作为最主要的数据输入类型。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/选举投票案例分析)

---

## 22 反射与工厂设计模式

### 22.1 工厂设计模式

> 工厂设计模式出现的主要目的是为了解决接口对象实例化的处理问题，利用关键字“new”直接在客户端进行指定接口对象的实例化处理，会造成接口与指定子类之间的耦合问题，因此可以引入一个专门的过渡类，负责接口对象的实例化操作，这就属于工厂类。

**范例：** 定义基础工厂类

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/6.
 */
interface IMessage {
    public void send(String msg);
}

class NewsPaper implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【报纸报道】：" + msg);
    }
}

class Factory {
    private Factory() {
    }

    public static IMessage getInstance(String className) {
        if ("newspaper".equalsIgnoreCase(className)) {
            return new NewsPaper();
        }
        return null;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage message = Factory.getInstance("NewsPaper"); // 获取指定接口实例
        message.send("今天有重大新闻报道～");
    }
}
```

**程序执行结果：**

```
【报纸报道】：今天有重大新闻报道～
```

> 对于此时的代码，是属于Java面向对象编程的基本模型，因为只要是获取接口实例，应该都通过工厂类来完成，于是当前的类设计结构如下：

———类图------

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与工厂设计模式/工厂设计模式/TestDemo.java)

### 22.2 问题来源

> 现在虽然实现了一个基础的工厂设计模式，但是实际上也存在有另外一个问题，如果要想扩充新的子类呢？那么此工厂类一定要发生变更。

**范例：** 传统的子类扩充

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/6.
 */
interface IMessage {
    public void send(String msg);
}

class NewsPaper implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【报纸报道】：" + msg);
    }
}

class NetMessage implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【网络消息】：" + msg);
    }
}

class Factory {
    private Factory() {
    }

    public static IMessage getInstance(String className) {
        if ("newspaper".equalsIgnoreCase(className)) {
            return new NewsPaper();
        } else if ("netmessage".equalsIgnoreCase(className)) {
            return new NetMessage();
        }
        return null;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage message = Factory.getInstance("NetMessage"); // 获取指定接口实例
        message.send("今天有重大新闻报道～");
    }
}
```

**程序执行结果：**

```
【网络消息】：今天有重大新闻报道～
```

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与工厂设计模式/问题来源/TestDemo.java)

### 22.3 问题解决

> 实际上这一切问题产生的根源在于关键字“new”，关键字“new”是Java提供的原生的对象实例化支持，但是却需要固定的结构才可以使用。如果要想解决关键字“new”的问题，最好的做法就是利用反射机制。

———类图------

**范例：** 通过反射修改工厂

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/6.
 */
interface IMessage {
    public void send(String msg);
}

class NewsPaper implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【报纸报道】：" + msg);
    }
}

class NetMessage implements IMessage {
    @Override
    public void send(String msg) {
        System.out.println("【网络消息】：" + msg);
    }
}

class Factory {
    private Factory() {
    }

    public static IMessage getInstance(String className) {
        IMessage instance = null;
        try {
            instance = (IMessage) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage message = Factory.getInstance("cn.ustb.demo.NetMessage"); // 获取指定接口实例
        if (message != null) {
            message.send("今天有重大新闻报道～");
        }
    }
}
```

**程序执行结果：**

```
【网络消息】：今天有重大新闻报道～
```

> 那么此时所编写的工厂类就可以适应各种环境，来获取IMessage接口实例。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与工厂设计模式/问题解决/TestDemo.java)

### 22.4 Annotation整合工厂设计模式

> Annotation最大的特点是基于注解进行配置的处理操作，所以可以见它与工厂设计模式进行一个有效的整合处理。现在，假设要进行消息的发送，那么消息发送可能是向数据库，也可能是向云服务器进行发送。

———tu---

**范例：** 利用Annotation简化工厂设计

```java
package cn.ustb.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MouseZhang on 2019/6/8.
 */

interface IConnect {
    public boolean build();
}

class DatabaseConnect implements IConnect {
    @Override
    public boolean build() {
        System.out.println("【DatabaseConnect】进行数据库资源的连接 ...");
        return true;
    }
}

class CloudConnect implements IConnect {
    @Override
    public boolean build() {
        System.out.println("【CloudConnect】进行云服务器资源的连接 ...");
        return true;
    }
}

@Target({ElementType.TYPE, ElementType.METHOD}) // 当前的Annotation可以出现在类和方法上
@Retention(RetentionPolicy.RUNTIME)
@interface Channel { // 自定义一个Annotation
    public String value(); // 设置一个操作的数据
}

@Channel("cn.ustb.demo.CloudConnect")
class Message {
    private IConnect connect;

    public Message() {
        // 1、获取本类上定义的Annotation对象信息
        Channel channel = super.getClass().getAnnotation(Channel.class);
        // 2、通过Annotation获取类的名称，利用反射加载类的实例
        try {
            this.connect = (IConnect) Class.forName(channel.value()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        if (this.connect.build()) {
            System.out.println("消息发送：" + msg);
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message message = new Message();
        message.send("www.ustb.edu.cn");
    }
}
```

**程序执行结果：**

```
【CloudConnect】进行云服务器资源的连接 ...
消息发送：www.ustb.edu.cn
```

> Annotation可以实现更加灵活地配置，简化工厂设计模式的开发结构。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与工厂设计模式/Annotation整合工厂设计模式/TestDemo.java)

------

## 23 反射与单例设计模式

### 23.1 单例设计模式

> 单例设计模式的核心本质在于，一个类在一个JVM进程之中只允许有一个实例化对象。单例模式根据设计情况分为两种：饿汉式单例、懒汉式单例，懒汉式的单例问题比较麻烦。

**范例：** 饿汉式单例设计模式

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public void print() {
        System.out.println("饿汉式单例设计模式");
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.print();
    }
}
```

**程序执行结果：**

```
饿汉式单例设计模式
```

> 之所以将其称为饿汉式的主要原因在于，Singleton里面会始终维持一个INSTANCE的实例化对象，而并不关心这个对象是否被使用。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与单例设计模式/单例设计模式/饿汉式单例设计模式/TestDemo.java)

**范例：** 懒汉式单例设计模式

```java
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
```

**程序执行结果：**

```
懒汉式单例设计模式
```

> 懒汉式单例模式的最大特点在于：第一次使用的时候才会进行实例化，不使用不进行实例化。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与单例设计模式/单例设计模式/懒汉式单例设计模式/TestDemo.java)

### 23.2 问题来源

**范例：** 观察懒汉式单例设计模式存在的问题

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
        System.out.println("【Singleton类构造】实例化Singleton类对象");
    }


    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
            }).start();
        }
    }
}
```

**程序执行结果：**

```
【Singleton类构造】实例化Singleton类对象
【Singleton类构造】实例化Singleton类对象
【Singleton类构造】实例化Singleton类对象
【Singleton类构造】实例化Singleton类对象
【Singleton类构造】实例化Singleton类对象
... ...
```

> 单例设计模式的本质在于，一个JVM进程只允许有该类的一个实例化对象，但是一旦发现使用了多线程，并结合懒汉式单例时，程序代码中出现了对象的多次实例化，若想解决当前存在的问题，就是利用同步处理。

———类图------

**范例：** 追加同步

```java
public synchronized static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
}
```

> 此时利用同步操作解决了单例实例化对象的问题，但是随之而来的将是严重的性能问题。假如有十万个线程获取Singleton类的实例化对象，那么最终会造成十万个线程要依次同步等待后获取。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与单例设计模式/问题来源/TestDemo.java)

### 23.3 问题解决

> 同步是否添加的决定性因素： **如果读取不要加同步，如果更新要加同步。**

——leitu------

**范例：** 解决懒汉式单例同步问题

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
class Singleton {
    private static Singleton instance;

    private Singleton() {
        System.out.println("【Singleton类构造】实例化Singleton类对象");
    }


    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
            }).start();
        }
    }
}
```

**程序执行结果：**

```
【Singleton类构造】实例化Singleton类对象
```

> 此时的代码既保证了getInstance()方法的操作性能，也保护了Singleton类对象的实例化次数。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与单例设计模式/问题解决/TestDemo.java)

------

## 24 反射与代理设计模式

### 24.1 静态代理设计模式缺陷

> 代理设计模式最为核心的意义在于，所有的操作业务接口都设置两个子类，一个子类负责真实的业务实现，另外一个子类负责代理业务操作，如果没有这个代理业务，真实业务也无法进行处理。
>
> 现假设希望可以实现一个数据的处理操作，在进行数据处理的时候，要求进行合理的事务控制，利用数据库的事务控制可以保证数据操作的完整性。

——tu----

**范例：** 编写静态代理设计

```java
package cn.ustb.demo;

/**
 * Created by MouseZhang on 2019/6/8.
 */

interface IMemberService { // 实现用户数据的操作
    public void add(); // 实现用户数据的追加
}

class MemberServiceImpl implements IMemberService {
    @Override
    public void add() {
        System.out.println("【真实业务主题】向数据库中执行\"INSERT INTO\"语句，进行数据添加。");
    }
}

class MemberServiceProxy implements IMemberService {
    private IMemberService realSubject; // 真实的操作业务

    public MemberServiceProxy(IMemberService realSubject) {
        this.realSubject = realSubject;
    }

    public boolean connect() {
        System.out.println("【代理主题】进行数据库的访问连接 ...");
        return true;
    }

    public void close() {
        System.out.println("【代理主题】关闭数据库的连接 ...");
    }

    public void transaction() {
        System.out.println("【代理主题】事务提交，进行数据更新处理 ...");
    }

    @Override
    public void add() {
        if (this.connect()) {
            this.realSubject.add();
            this.transaction();
            this.close();
        }
    }
}

class Factory {
    private Factory() {
    }

    public static IMemberService getInstance() {
        return new MemberServiceProxy(new MemberServiceImpl());
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMemberService memberService = Factory.getInstance();
        memberService.add();
    }
}
```

**程序执行结果：**

```
【代理主题】进行数据库的访问连接 ...
【真实业务主题】向数据库中执行"INSERT INTO"语句，进行数据添加。
【代理主题】事务提交，进行数据更新处理 ...
【代理主题】关闭数据库的连接 ...
```

> 但是现在需要进一步思考一个问题，在一个数据库里，有可能会存在上百张表，如果每一张数据表都存在有一个Service接口，这时代理就会泛滥，而且会发现所有的代理步骤几乎相似。所以之前的静态代理设计模式只能够满足于一个操作接口的要求，而无法满足于所有操作接口的需求。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与代理设计模式/静态代理设计模式缺陷/TestDemo.java)

### 24.2 动态代理设计模式

> 如果要想解决静态代理设计模式存在的代码重复操作问题，就只能够利用动态代理设计模式来解决，动态代理设计模式指的是一组相关操作接口的实现，可以设置统一的代理类。
>
> 动态代理类是在JDK1.3的时候添加到项目之中的，如果要想实现动态代理类需要"Invocation"接口和"Proxy"类的支持，观察下面java.lang.reflect.InvocationHandle接口的定义：

```java
public Interface InvocationHandler {
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;	
}
```

> 此时对于代理设计需要解决的一个核心问题在于，如何可以让InvocationHandler子类和要代理操作的业务接口产生关联，所以此时就需要通过java.lang.reflect.Proxy类来进行关联的创建，创建方法为：

```java
public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
```

> 这种代理对象的创建是依据真实主题类对象的加载器，和其实现的父接口动态创建的一个新的子类，该子类由JVM在运行时自行负责创建。

—tu---

**范例：** 编写动态代理设计

```java
package cn.ustb.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by MouseZhang on 2019/6/8.
 */

interface IMemberService { // 实现用户数据的操作
    public void add(); // 实现用户数据的追加
}

class MemberServiceImpl implements IMemberService {
    @Override
    public void add() {
        System.out.println("【真实业务主题】向数据库中执行\"INSERT INTO\"语句，进行数据添加。");
    }
}

class ServiceProxy implements InvocationHandler {
    private Object target; // 真实业务主题对象

    /**
     * 绑定真实主题对象，同时返回代理实例
     * @param target 真实的接口操作对象，利用反射可以追溯其来源
     * @return 代理对象
     */
    public Object bind(Object target) {
        this.target = target; // 保存真实业务对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public boolean connect() {
        System.out.println("【代理主题】进行数据库的访问连接 ...");
        return true;
    }

    public void close() {
        System.out.println("【代理主题】关闭数据库的连接 ...");
    }

    public void transaction() {
        System.out.println("【代理主题】事务提交，进行数据更新处理 ...");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = null;
        if (this.connect()) {
            returnValue = method.invoke(this.target, args); // 调用真实业务主题
            this.transaction();
            this.close();
        }
        return returnValue;
    }
}

class Factory {
    private Factory() {
    }

    public static IMemberService getInstance() {
        return (IMemberService) new ServiceProxy().bind(new MemberServiceImpl());
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMemberService memberService = Factory.getInstance(); // 得到一个JVM动态实例化的子类对象
        memberService.add();
    }
}
```

**程序执行结果：**

```
【代理主题】进行数据库的访问连接 ...
【真实业务主题】向数据库中执行"INSERT INTO"语句，进行数据添加。
【代理主题】事务提交，进行数据更新处理 ...
【代理主题】关闭数据库的连接 ...
```

> 此时的代码利用动态代理设计，动态地构建了接口的实现子类实例，并利用InvocationHandler.invoke()实现标准的代码执行调用和控制。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与代理设计模式/动态代理设计模式/TestDemo.java)

----

## 25 反射与简单Java类

### 25.1 传统属性赋值弊端

> 简单Java类最大的特点在于其主要进行数据的存储，并不进行任何复杂的业务判断，即：对于所谓的循环、分支等语句实际上在开发中是不会出现在简单Java类中的，现假设有如下的一个简单Java类。

**范例：** 定义一个描述雇员信息的类

```java
package cn.ustb.vo;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class Emp {
    private String ename;
    private String job;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
```

> 此时有了Emp类之后，肯定需要进行属性的设置，那么传统的属性设置必须依据对应的属性名称进行处理，现假设要操作的属性内容全部都在一个Map集合里（模拟输入类）。

**范例：** 定义一个模拟输入类

```java
package cn.ustb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class InputData { // 模拟一个键盘的输入
    public static Map<String, String> input() {
        Map<String, String> map = new HashMap<String, String>(); // key为属性名称，value为属性内容
        map.put("ename", "小张");
        map.put("job", "办事员");
        return map;
    }
}
```

> 此时模拟了一个键盘输入的处理操作，其中Map集合的key和雇员类中的属性名称是相同的。

——tu ----

**范例：** 实现Map集合向Emp对象的数据保存

```java
package cn.ustb.demo;

import cn.ustb.util.InputData;
import cn.ustb.vo.Emp;

import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class TestDemo {
    public static void main(String[] args) {
        Emp emp = new Emp();
        Map<String, String> input = InputData.input(); // 获取输入数据
        emp.setEname(input.get("ename"));
        emp.setJob(input.get("job"));
        System.out.println(emp);
    }
}
```

**程序执行结果：**

```
Emp{ename='小张', job='办事员'}
```

——图----

> 思考下面两个问题：
>
> - 现在Emp类中是两个属性，但是如果这个类提供有100个属性时，意味着setter需要编写100次；
> - 那么如果要想进行一些合理的设计，必然要求可以满足于所有的简单Java类。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/反射与简单Java类/传统属性赋值弊端)

### 25.2 自动赋值实现思路

> 通过分析可以发现，如果要是将输入返回的Map集合直接转为简单Java类中操作的对象数据是最方便的，此时就必须利用反射机制来实现。

——tu----

**范例：** 程序的实现思路

```java
package cn.ustb.util.reflect;

import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class ObjectInstanceFactory {
    private ObjectInstanceFactory() {
    }

    /**
     * 根据传入的Class类型获取指定类型的实例化对象，同时可以将传入的属性进行赋值（错误的属性不赋值）
     * @param clazz 要进行实例化对象的简单Java类型
     * @param value 包含有输入数据的Map集合，其中key和value的类型必须是String
     * @param <T>   根据传入的Class类型获取一个具体的实例
     * @return 带有属性内容的简单Java类对象
     */
    public static <T> T create(Class<?> clazz, Map<String, String> value) {
        return null;
    }
}
```

> 在外部调用的时候不关心里面具体如何实现的属性处理配置，只关心最终能否获取实例化对象，所以外部调用形式如下。

**范例：** 外部调用形式

```java
package cn.ustb.demo;

import cn.ustb.util.InputData;
import cn.ustb.util.reflect.ObjectInstanceFactory;
import cn.ustb.vo.Emp;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class TestDemo {
    public static void main(String[] args) {
        Emp emp = ObjectInstanceFactory.create(Emp.class, InputData.input());
        System.out.println(emp);
    }
}
```

> 实现的时候必须考虑所有类型的设计问题，因为该程序类可以实现各种简单Java类对象的创建操作。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/反射与简单Java类/自动赋值实现思路)

### 25.3 单级属性赋值

> 所谓的单级属性指的是该类不存在有其它简单Java类的引用关联，在本类之中只考虑当前的常用类型，为了简化设计，本次先考虑当前类型是String的形式。

-----tu----

**范例：** 定义BeanUtil类

```java
package cn.ustb.util.reflect;

import cn.ustb.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class BeanUtil {
    private BeanUtil() {
    }

    public static void setValue(Object object, Map<String, String> map) {
        Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); // 获取Iterator接口实例
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next(); // 获取每一组数据
            try { // 防止某些成员输入错误而导致所有的成员赋值出错
                Field field = object.getClass().getDeclaredField(entry.getKey());
                // 依据传入的属性名称（key）获取相应的setter方法，并利用Field获取方法参数类型
                Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtil.initcap(entry.getKey()), field.getType());
                setMethod.invoke(object, entry.getValue()); // 反射调用setter方法并设置属性内容
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

**范例：** ObjectInstanceFactory类只是简单地进行调用

```java
package cn.ustb.util.reflect;

import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class ObjectInstanceFactory {
    private ObjectInstanceFactory() {
    }

    /**
     * 根据传入的Class类型获取指定类型的实例化对象，同时可以将传入的属性进行赋值（错误的属性不赋值）
     * @param clazz 要进行实例化对象的简单Java类型
     * @param value 包含有输入数据的Map集合，其中key和value的类型必须是String
     * @param <T>   根据传入的Class类型获取一个具体的实例
     * @return 带有属性内容的简单Java类对象
     */
    public static <T> T create(Class<?> clazz, Map<String, String> value) {
        Object object = null;
        try {
            // 1、在工厂类中调用类中的无参构造方法进行对象实例化处理
            object = clazz.getDeclaredConstructor().newInstance();
            // 2、进行内容的设置，利用反射进行处理
            BeanUtil.setValue(object, value); // 赋值交由其它的类完成
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.util.InputData;
import cn.ustb.util.reflect.ObjectInstanceFactory;
import cn.ustb.vo.Emp;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class TestDemo {
    public static void main(String[] args) {
        Emp emp = ObjectInstanceFactory.create(Emp.class, InputData.input());
        System.out.println(emp);
    }
}
```

**程序执行结果：**

```
Emp{ename='小张', job='办事员'}
```

> 成功地实现了对象中属性的赋值操作。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/反射与简单Java类/单级属性赋值)

### 25.4 设置多种数据类型

> 在之前的代码里面为了方便起见，所有的数据类型都只是简单的考虑到了String的问题，但是在现实的开发中，对于使用的数据类型比较常见的为：Integer(int)、Double(double)、Long(long)、Date(日期、日期时间)。该操作主要在BeanUtil类中追加一个数据的转换处理方法，根据操作属性的类型实现转换处理。

——tu---

**范例：** 在BeanUtil类中定义一个数值转换方法

```java
/**
 * 实现字符串数据向指定数据类型的转换
 * @param value 接收输入的字符串数据内容，所有的内容均为String
 * @param field 要转换的目标类型
 * @return 转换的数据结构
 */
private static Object convertValue(String value, Field field) {
    String fieldName = field.getType().getName();
    if ("java.lang.String".equalsIgnoreCase(fieldName)) {
        return value; // 不转换，直接返回
    }
    if ("int".equalsIgnoreCase(fieldName) || "java.lang.Integer".equalsIgnoreCase(fieldName)) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
    if ("long".equalsIgnoreCase(fieldName) || "java.lang.Long".equalsIgnoreCase(fieldName)) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0;
        }
    }
    if ("double".equalsIgnoreCase(fieldName) || "java.lang.Double".equalsIgnoreCase(fieldName)) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
    if ("java.util.Date".equalsIgnoreCase(fieldName)) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            SimpleDateFormat sdf = null;
            if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else if (value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            try {
                return sdf.parse(value);
            } catch (Exception e) {
                return null;
            }
        }
    }
    return null;
}
```

**范例：** 修改Emp类定义，追加新的属性

```java
package cn.ustb.vo;

import java.util.Date;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class Emp {
    private Long empno;
    private String ename;
    private String job;
    private Integer age;
    private Date hiredate;
    private Double sal;

    public Long getEmpno() {
        return empno;
    }

    public void setEmpno(Long empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", age=" + age +
                ", hiredate=" + hiredate +
                ", sal=" + sal +
                '}';
    }
}
```

**范例：** 修改输入数据部分

```java
package cn.ustb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class InputData { // 模拟一个键盘的输入
    public static Map<String, String> input() {
        Map<String, String> map = new HashMap<String, String>(); // key为属性名称，value为属性内容
        map.put("empno", "21095");
        map.put("ename", "小张");
        map.put("job", "办事员");
        map.put("hiredate", "2005-10-15");
        map.put("age", "22");
        map.put("sal", "3580.27");
        return map;
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.util.InputData;
import cn.ustb.util.reflect.ObjectInstanceFactory;
import cn.ustb.vo.Emp;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class TestDemo {
    public static void main(String[] args) {
        Emp emp = ObjectInstanceFactory.create(Emp.class, InputData.input());
        System.out.println(emp);
    }
}
```

**程序执行结果：**

```
Emp{empno=21095, ename='小张', job='办事员', age=22, hiredate=Sat Oct 15 00:00:00 CST 2005, sal=3580.27}
```

> 此时已经将可以考虑到的数据类型全部归纳到程序之中了。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/反射与简单Java类/设置多种数据类型)

### 25.5 多级对象实例化

> 对于当前结构主要是针对Emp的形式来完成的，所以在输入数据的时候，Map集合中key的数据全部都是单级属性，但是在一些时候也可能继续进行其它引用数据的设置。现假设有如下的关系：一个雇员属于一个部门，一个部门属于一个公司，因此可以进行如下三个类的设计：

—biaoge ----

——tu ----

> 如果此时要进行级联数据的设计，则必须对输入数据的结构上有所要求，可以修改如下的输入数据格式。

—tu---

> 所以要想完成后续的设计，那么就必须考虑当前类对象实例化的问题，级联对象的实例化必须优先设计，但是一定要保证，在一个简单Java类赋值过程之中，该实例化对象只能够实例化一次，并且简单Java类要提供有无参构造方法。

**范例：** 修改BeanUtil类

```java
public static void setValue(Object object, Map<String, String> map) {
    Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); // 获取Iterator接口实例
    while (iter.hasNext()) {
        Map.Entry<String, String> entry = iter.next(); // 获取每一组数据
        try { // 防止某些成员输入错误而导致所有的成员赋值出错
            if (entry.getKey().contains(".")) { // 此时就有可能出现有级联关系
                // 依据"."进行拆分处理，而后依次判断，如果发现getter方法调用返回的是null，则利用setter实例化
                String[] fieldSplit = entry.getKey().split("\\."); // 进行拆分处理操作
                Object currentObject = object; // 设置一个当前的操作对象（后面会不断修改其引用）
                for (int i = 0; i < fieldSplit.length - 1; i++) { // 循环每一个属性
                    Method getMethod = currentObject.getClass().getDeclaredMethod("get" + StringUtil.initcap(fieldSplit[i]));
                    Object tempReturn = getMethod.invoke(currentObject);
                    if (tempReturn == null) { // 当前的对象并未实例化，应该调用setter设置内容
                        Class<?> currentType = currentObject.getClass().getDeclaredField(fieldSplit[i]).getType();
                        Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtil.initcap(fieldSplit[i]), currentType);
                        tempReturn = currentType.getDeclaredConstructor().newInstance();
                        setMethod.invoke(currentObject, tempReturn);
                    }
                    currentObject = tempReturn;
                }
            } else {
                Field field = object.getClass().getDeclaredField(entry.getKey());
                // 依据传入的属性名称（key）获取相应的setter方法，并利用Field获取方法参数类型
                Method setMethod = object.getClass().getDeclaredMethod("set" + StringUtil.initcap(entry.getKey()), field.getType());
                setMethod.invoke(object, convertValue(entry.getValue(), field)); // 反射调用setter方法并设置属性内容
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

> 此时实现了正常的级联类对象的反射实例化。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/blob/master/反射与简单Java类/多级对象实例化/BeanUtil.java)

### 25.6 多级属性赋值

> 现在既然可以实现级联对象的实例化处理，那么自然也就应该可以针对级联属性赋值进行处理，级联属性赋值最关键的问题在于如何获取要操作的对象。

**范例：** 修改BeanUtil类

```java
public static void setValue(Object object, Map<String, String> map) {
    Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator(); // 获取Iterator接口实例
    while (iter.hasNext()) {
        Map.Entry<String, String> entry = iter.next(); // 获取每一组数据
        String fieldKey = null;
        Object currentObject = object; // 设置一个当前的操作对象（后面会不断修改其引用）
        try { // 防止某些成员输入错误而导致所有的成员赋值出错
            if (entry.getKey().contains(".")) { // 此时就有可能出现有级联关系
                // 依据"."进行拆分处理，而后依次判断，如果发现getter方法调用返回的是null，则利用setter实例化
                String[] fieldSplit = entry.getKey().split("\\."); // 进行拆分处理操作
                for (int i = 0; i < fieldSplit.length - 1; i++) { // 循环每一个属性
                    Method getMethod = currentObject.getClass().getDeclaredMethod("get" + StringUtil.initcap(fieldSplit[i]));
                    Object tempReturn = getMethod.invoke(currentObject);
                    if (tempReturn == null) { // 当前的对象并未实例化，应该调用setter设置内容
                        Class<?> currentType = currentObject.getClass().getDeclaredField(fieldSplit[i]).getType();
                        Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtil.initcap(fieldSplit[i]), currentType);
                        tempReturn = currentType.getDeclaredConstructor().newInstance();
                        setMethod.invoke(currentObject, tempReturn);
                    }
                    currentObject = tempReturn;
                }
                fieldKey = entry.getKey().substring(entry.getKey().lastIndexOf(".") + 1); // 属性
            } else {
                fieldKey = entry.getKey(); // 直接获取属性名称
            }
            Field field = currentObject.getClass().getDeclaredField(fieldKey);
            // 依据传入的属性名称（key）获取相应的setter方法，并利用Field获取方法参数类型
            Method setMethod = currentObject.getClass().getDeclaredMethod("set" + StringUtil.initcap(fieldKey), field.getType());
            setMethod.invoke(currentObject, convertValue(entry.getValue(), field)); // 反射调用setter方法并设置属性内容
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**范例：** 编写测试程序

```java
package cn.ustb.demo;

import cn.ustb.util.InputData;
import cn.ustb.util.reflect.ObjectInstanceFactory;
import cn.ustb.vo.Emp;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class TestDemo {
    public static void main(String[] args) {
        Emp emp = ObjectInstanceFactory.create(Emp.class, InputData.input());
        System.out.println(emp);
    }
}
```

**程序执行结果：**

```
Emp{empno=21095, ename='小张', job='办事员', age=22, hiredate=Sat Oct 15 00:00:00 CST 2005, sal=3580.27, dept=Dept{dname='软件开发部', loc='北京', company=Company{cname='USTB', createDate=Sun Apr 22 00:00:00 CST 1962}}}
```

> 此时的程序就可以实现最终的级联属性的设置，而且可以随意设置多级层次关系。

- [全部代码](https://github.com/MouseZhang/Java-Code-Notebook/tree/master/反射与简单Java类/多级属性赋值)

----

