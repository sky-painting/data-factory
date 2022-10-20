package com.tianhua.datafactory.client.function;

/**
 * Description
 * date: 2022/8/9
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface FunctionProxy<T>{

    public T createOneData(String dataSource, String... params);

}
