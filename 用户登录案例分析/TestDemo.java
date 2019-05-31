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
