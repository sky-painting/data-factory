package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
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
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CHINESE_NAME)
public class CurrentTimeFunc {

}
