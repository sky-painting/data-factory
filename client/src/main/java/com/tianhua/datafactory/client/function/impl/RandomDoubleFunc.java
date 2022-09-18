package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;

/**
 * Description
 * date: 2022/9/18
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "randomDoubleFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.RANDOM_DOUBLE)
public class RandomDoubleFunc implements Function<Double> {
    @Resource(name = "randomNumFunc")
    private Function randomNumFunc;

    private static SecureRandom secureRandom = new SecureRandom();

    @Override
    public Double createOneData(String... params) {
        //小数点后两位
        int len = 2;
        if(params != null && StringUtils.isNotEmpty(params[0])){
            len = Integer.parseInt(params[0]);
        }

        String first = randomNumFunc.createOneData((secureRandom.nextInt(5)+1)+"").toString();
        String second = randomNumFunc.createOneData(len + "").toString();

        String floatValue = first + "." + second;

        return Double.parseDouble(floatValue);
    }
}
