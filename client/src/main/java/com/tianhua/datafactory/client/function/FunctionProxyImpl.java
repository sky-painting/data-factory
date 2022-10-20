package com.tianhua.datafactory.client.function;

import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * 基于缓存构建
 *
 * 函数代理
 * 内置函数
 * 文件
 * kv配置
 * date: 2022/8/9
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FunctionProxyImpl implements FunctionProxy{

    @Autowired
    private FunctionFactory functionFactory;

    @Override
    public Object createOneData(String dataSource, String... params) {
        functionFactory.createFunction(dataSource);
        return null;
    }


}
