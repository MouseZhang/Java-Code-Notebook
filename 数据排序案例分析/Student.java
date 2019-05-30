package cn.ustb.co;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Student implements Comparable<Student> {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        if (this.score < o.score) {
            return 1;
        } else if (this.score > o.score) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "姓名：" + this.name + "、成绩：" + this.score;
    }
}
