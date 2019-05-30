package cn.ustb.service;

import cn.ustb.co.Student;
import cn.ustb.factory.Factory;
import cn.ustb.validate.IValidator;

import java.util.Arrays;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class MainService { //
    private IInputData input = Factory.getInputDataInstance();
    private IValidator validator = Factory.getValidatorInstance("([a-zA-Z]+:\\d{1,3}(\\.\\d{1,2})?\\|?)+");
    private String inputData;
    private Student[] students;

    public void input() {
        boolean flag = true;
        while (flag) {
            String value = this.input.getStringNotNull("请输入统计记录（格式：\"姓名:成绩|姓名:成绩|...\"）：");
            if (this.validator.check(value)) { // 验证通过
                this.inputData = value;
                this.handleData(); // 进行数据处理
                flag = false; // 结束循环
            } else {
                System.err.println("数据格式输入错误，请重新输入！");
            }
        }
    }

    private void handleData() {
        String[] result = this.inputData.split("\\|");
        this.students = new Student[result.length];
        for (int i = 0; i < students.length; i++) {
            String[] temp = result[i].split(":");
            students[i] = new Student(temp[0], Double.parseDouble(temp[1]));
        }
        Arrays.sort(this.students);
    }

    public Student[] getStudents() {
        return this.students;
    }
}
