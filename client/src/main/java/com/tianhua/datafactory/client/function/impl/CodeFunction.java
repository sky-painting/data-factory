package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

/**
 * Description:
 * date: 2022/6/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "codeFunction")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CARD_NUMBER)
public class CodeFunction implements Function<String> {


    @Override
    public String createOneData(String methodName, String... params) {
        return null;
    }




}
