package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

/**
 * Description
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CURRENT_TIME)
public class CurrentTimeFunc implements Function<Long> {

    @Override
    public Long createOneData(String... params) {
        return System.currentTimeMillis();
    }
}
