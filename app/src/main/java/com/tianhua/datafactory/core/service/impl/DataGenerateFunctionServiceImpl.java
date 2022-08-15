package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
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
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataBuildRequestFieldRuleBO();

        Function function = dataSourceFieldRequestBean.getFunction();


        Object value = function.createOneData(dataBuildRequestFieldRuleBO.getFuncVar());
        return value;

    }
}
