package cn.ustb.service;

import cn.ustb.factory.Factory;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class MainService {
    private IInputData input = Factory.getInputDataInstance();
    private String username;
    private String password;
    private static int count = 0; // 记录登录次数

    public void input() { // 实现数据的输入处理
        String content = this.input.getStringNotNull("请输入登录用户名：");
        if (content.contains("/")) { // 如果输入用户名和密码
            String[] result = content.split("/");
            if (!content.endsWith("/")) {
                if (result.length == 2) { // 长度正确
                    username = result[0];
                    password = result[1];
                }
            } else {
                this.username = result[0];
                this.password = this.input.getStringNotNull("请输入登录密码：");
            }
        } else {  // 只输入了用户名
            this.username = content;
            this.password = this.input.getStringNotNull("请输入登录密码：");
        }
    }

    public String login() {
        ILoginService loginService = Factory.getLoginServiceInstance(this.username, this.password);
        if (count >= 2) {
            return "登录尝试次数过多，无法登录，程序退出！";
        }
        if (loginService.login()) {
            return "登录成功，欢迎" + this.username + "光临！";
        } else {
            System.err.println("登录失败，错误的用户名和密码！");
            count++;
            this.input(); // 重新进行登录输入
            return this.login();
        }
    }
}
