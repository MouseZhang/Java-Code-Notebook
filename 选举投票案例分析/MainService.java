package cn.ustb.service;

import cn.ustb.co.Student;
import cn.ustb.factory.Factory;

import java.util.Arrays;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class MainService {
    private Student[] students = {new Student(1, "张三", 0), new Student(2, "李四", 0),
            new Student(3, "王五", 0), new Student(4, "赵六", 0)};
    private IInputData input = Factory.getInputDataInstance();
    private boolean flag = true;

    public MainService() {
        this.show();
        while (flag) {
            this.vote();
        }
        this.show();
        this.result();
    }

    public void show() { // 显示所有候选人信息
        for (Student stu : this.students) {
            System.out.println(stu);
        }
    }

    public void vote() { // 开始投票
        int num = input.getIntNotNull("请输入班长候选人代号（数字0结束）：");
        switch (num) {
            case 0: {
                this.flag = false;
                break;
            }
            case 1: {
                this.students[0].setVote(this.students[0].getVote() + 1);
                break;
            }
            case 2: {
                this.students[1].setVote(this.students[1].getVote() + 1);
                break;
            }
            case 3: {
                this.students[2].setVote(this.students[2].getVote() + 1);
                break;
            }
            case 4: {
                this.students[3].setVote(this.students[3].getVote() + 1);
                break;
            }
            default: {
                System.out.println("此选票无效，请输入正确的候选人代号！");
            }
        }
    }

    public void result() {
        Arrays.sort(this.students);
        System.out.println("投票最终结果：" + this.students[0].getName() + "同学，最后以" + this.students[0].getVote() + "票当选班长！");
    }
}
