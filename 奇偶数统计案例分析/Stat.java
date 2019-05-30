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
