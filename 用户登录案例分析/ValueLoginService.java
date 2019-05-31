package cn.ustb.service.impl;

import cn.ustb.service.ILoginService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class ValueLoginService implements ILoginService {
    private String username;
    private String password;

    public ValueLoginService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login() {
        return "user".equals(this.username) && "12345".equals(this.password);
    }
}
