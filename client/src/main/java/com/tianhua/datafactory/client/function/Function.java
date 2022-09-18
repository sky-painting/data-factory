package com.tianhua.datafactory.client.function;

/**
 * description: Function 生成随机数据的函数接口
 * date: 2020/12/28 23:38 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface Function<T> {
    /**
     * 创建一个数据
     * @param params 参数列表,控制随机函数可以依赖的参数
     * @return
     */
    T createOneData(String... params) throws Exception;

}
