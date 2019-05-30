package cn.ustb.factory;

import cn.ustb.service.IInputData;
import cn.ustb.service.KeyboardInputData;
import cn.ustb.validate.IValidator;
import cn.ustb.validate.impl.RegexValidator;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public class Factory {
    private Factory() {
    }

    public static IInputData getInputDataInstance() {
        return new KeyboardInputData();
    }

    public static IValidator getValidatorInstance(String regex) {
        return new RegexValidator(regex);
    }
}
