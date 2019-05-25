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
