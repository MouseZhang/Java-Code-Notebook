package cn.ustb.factory;

import cn.ustb.service.IInputData;
import cn.ustb.service.KeyboardInputData;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Factory {
    private Factory() {
    }

    public static IInputData getInputDataInstance() {
        return new KeyboardInputData();
    }
}
