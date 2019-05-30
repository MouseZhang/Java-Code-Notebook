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
