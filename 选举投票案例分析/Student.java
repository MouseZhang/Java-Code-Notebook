package cn.ustb.co;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class Student implements Comparable<Student> {
    private int stuNo;
    private String name;
    private int vote;

    public Student(int stuNo, String name, int vote) {
        this.setStuNo(stuNo);
        this.setName(name);
        this.setVote(vote);
    }

    public int getStuNo() {
        return stuNo;
    }

    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public int compareTo(Student o) {
        if (this.vote < o.vote) {
            return 1;
        } else if (this.vote > o.vote) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.getStuNo() + "：" + this.getName() + "【" + this.getVote() + "】";
    }
}
