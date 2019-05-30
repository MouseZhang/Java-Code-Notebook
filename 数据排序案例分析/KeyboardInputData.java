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
    public String getStringNotNull(String prompt) {
        boolean flag = true;
        String value = null;
        while (flag) {
            value = this.getString(prompt);
            if (!(value == null || "".equals(value))) {
                flag = false;
            } else {
                System.err.println("输入的数据不允许为空！");
            }
        }
        return value;
    }
}
