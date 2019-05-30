package cn.ustb.validate.impl;

import cn.ustb.validate.IValidator;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class RegexValidator implements IValidator { // 正则验证器
    private String regex;

    public RegexValidator(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean check(String value) {
        if (value == null || "".equals(value)) {
            return false;
        }
        return value.matches(this.regex);
    }
}
