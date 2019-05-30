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
