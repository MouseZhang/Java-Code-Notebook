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
