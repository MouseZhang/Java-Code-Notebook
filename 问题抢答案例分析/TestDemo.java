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
