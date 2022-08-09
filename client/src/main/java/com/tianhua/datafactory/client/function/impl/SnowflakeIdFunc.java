package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

/**
 * Description
 * date: 2022/8/8
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "snowflakeIdFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.SNOWFLAKE_ID)
public class SnowflakeIdFunc implements Function<String> {

    @Override
    public String createOneData(String... params) {
        return SnowflakeIdWorker.getNextId();
    }
}
