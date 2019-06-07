package cn.ustb.vo;

import java.util.Date;

/**
 * Created by MouseZhang on 2019/6/7.
 */
public class Company {
    private String cname;
    private Date createDate;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Company{" +
                "cname='" + cname + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
