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
