package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.client.function.Function;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldRuleBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.service.DataGenerateService;
import org.springframework.stereotype.Service;

/**
 * description: DataGenerateFunctionServiceImpl <br>
 * date: 2021/1/17 20:29 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateFunctionServiceImpl")
public class DataGenerateFunctionServiceImpl implements DataGenerateService {
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        Function function = dataSourceFieldRequestBean.getFunction();
        DataFactoryRequestFieldRuleBean dataFactoryRequestFieldRuleBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean().getDataFactoryRequestFieldRuleBean();
        if (dataFactoryRequestFieldRuleBean == null) {
            return function.createOneData(null, null);
        } else {
            //todo 前缀值，后缀值处理
            return function.createOneData(dataFactoryRequestFieldRuleBean.getDepencyFunctionMethod(), dataFactoryRequestFieldRuleBean.getDepencyFunctionMethodParam());
        }
    }
}
