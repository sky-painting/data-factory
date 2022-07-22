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
     * @param methodName 方法名，一个function类中有多个method，如果没有的话默认传空
     * @return
     */
    T createOneData(String methodName, String... params);

}
