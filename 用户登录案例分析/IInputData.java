package cn.ustb.service;

/**
 * Created by MouseZhang on 2019/5/30.
 */
public interface IInputData {
    /**
     * 实现字符串数据的输入
     *
     * @param prompt 输入时的提示信息
     * @return 返回一个输入的字符串（可以为空）
     */
    public String getString(String prompt);

    /**
     * 实现字符串数据的输入
     *
     * @param prompt 输入时的提示信息
     * @return 返回一个不为空的字符串
     */
    public String getStringNotNull(String prompt);
}
