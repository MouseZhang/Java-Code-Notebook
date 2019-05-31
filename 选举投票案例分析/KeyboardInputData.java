package cn.ustb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class KeyboardInputData implements IInputData {
    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getString(String prompt) {
        System.out.println(prompt);
        String value = null;
        try {
            value = INPUT.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public int getIntNotNull(String prompt) {
        boolean flag = true;
        int number = 0;
        while (flag) {
            String value = this.getString(prompt); // 接收数据的输入
            if (!(value == null || "".equals(value))) {
                if (value.matches("\\d+")) { // 判断是数字
                    number = Integer.parseInt(value);
                    flag = false;
                } else {
                    System.err.println("此选票无效，请输入正确的候选人代号！");
                }
            } else {
                System.err.println("此选票无效，请输入正确的候选人代号！");
            }
        }
        return number;
    }
}
