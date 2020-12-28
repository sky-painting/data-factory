package com.coderman.tianhua.datafactory.core.function;

/**
 * description: Function <br>
 * date: 2020/12/28 23:38 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface Function<T> {
    /**
     * 创建一个数据
     * @return
     */
    T createOneData();
}
