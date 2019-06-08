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
