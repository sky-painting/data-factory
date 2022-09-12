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
 *
 * 随机浮点数
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "randomFloatFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.RANDOM_FLOAT)
public class RandomFloatFunc implements Function<String> {

    @Resource(name = "randomNumFunc")
    private Function randomNumFunc;

    private static SecureRandom secureRandom = new SecureRandom();

    @Override
    public String createOneData(String... params) {
        //小数点后两位
        int len = 2;
        if(params != null && StringUtils.isNotEmpty(params[0])){
            len = Integer.parseInt(params[0]);
        }

        String first = randomNumFunc.createOneData((secureRandom.nextInt(5)+1)+"").toString();
        String second = randomNumFunc.createOneData(len + "").toString();

        return first + "." + second;
    }
}
