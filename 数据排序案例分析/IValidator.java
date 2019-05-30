package cn.ustb.validate;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public interface IValidator {
    /**
     * 实现验证的处理操作
     * @param value 要验证的数据
     * @return 验证成功返回true，否则返回false
     */
    public boolean check(String value); // 正则验证处理
}
