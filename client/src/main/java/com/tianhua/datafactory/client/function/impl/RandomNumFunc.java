package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.apache.commons.lang3.StringUtils;

/**
 * Description
 * 获取一个指定位数的随机数--可以当做密码，or验证码
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.RANDOM)
public class RandomNumFunc  implements Function<Integer> {

    @Override
    public Integer createOneData(String... params) {
        int len = 0;

        if(params == null || StringUtils.isEmpty(params[0])){
            len = 6;
        }
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return rs;
    }
}
