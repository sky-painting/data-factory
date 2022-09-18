package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
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
    private FunctionFactory functionFactory;

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataBuildRequestFieldRuleBO();

        String dataSourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();
        Function function = functionFactory.createFunction(dataSourceCode);

        if(dataBuildRequestFieldRuleBO == null){
            return function.createOneData(null);
        }

        Object objValue = function.createOneData(dataBuildRequestFieldRuleBO.getFuncVar());
        return objValue;
    }
}
