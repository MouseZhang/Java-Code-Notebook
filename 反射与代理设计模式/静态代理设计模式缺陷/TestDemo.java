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
