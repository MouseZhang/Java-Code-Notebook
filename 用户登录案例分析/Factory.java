package cn.ustb.factory;

import cn.ustb.service.IInputData;
import cn.ustb.service.ILoginService;
import cn.ustb.service.KeyboardInputData;
import cn.ustb.service.impl.ValueLoginService;

/**
 * Created by MouseZhang on 2019/5/31.
 */
public class Factory {
    private Factory() {
    }

    public static IInputData getInputDataInstance() {
        return new KeyboardInputData();
    }

    public static ILoginService getLoginServiceInstance(String username, String password) {
        return new ValueLoginService(username, password);
    }
}
