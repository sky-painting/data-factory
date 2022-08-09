package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Description
 * 获取一个指定位数的随机数--可以当做密码，or验证码
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "randomNumFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.RANDOM)
public class RandomNumFunc  implements Function<Integer> {

    @Override
    public Integer createOneData(String... params) {
        int len = 6;

        if(params != null && StringUtils.isNotEmpty(params[0])){
            len = Integer.parseInt(params[0]);
        }
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return rs;
    }
}
