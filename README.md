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

