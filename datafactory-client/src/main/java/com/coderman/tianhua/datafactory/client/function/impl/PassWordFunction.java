package com.coderman.tianhua.datafactory.client.function.impl;

import com.coderman.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.coderman.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.coderman.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

/**
 * description: PassWordFunction <br>
 * date: 2021/1/28 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "passWordFunction")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.PASS_WORD)
public class PassWordFunction implements Function<String> {
    @Override
    public String createOneData(String methodName, String... params) {
        return null;
    }
}
