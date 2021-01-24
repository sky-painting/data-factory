package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.client.function.Function;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldRuleBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.tianhua.datafactory.core.service.DataValueHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: DataGenerateFunctionServiceImpl <br>
 * date: 2021/1/17 20:29 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateFunctionServiceImpl")
public class DataGenerateFunctionServiceImpl implements DataGenerateService {

    @Autowired
    private DataValueHandler dataValueHandler;

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        Function function = dataSourceFieldRequestBean.getFunction();
        DataBuildRequestFieldRuleBean dataFactoryRequestFieldRuleBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean().getDataFactoryRequestFieldRuleBean();
        if (dataFactoryRequestFieldRuleBean == null) {
            return function.createOneData(null, null);
        } else {
            Object value = function.createOneData(dataFactoryRequestFieldRuleBean.getDepencyFunctionMethod(), dataFactoryRequestFieldRuleBean.getDepencyFunctionMethodParam());
            return dataValueHandler.handleValue(value,dataFactoryRequestFieldRuleBean);
        }
    }
}
