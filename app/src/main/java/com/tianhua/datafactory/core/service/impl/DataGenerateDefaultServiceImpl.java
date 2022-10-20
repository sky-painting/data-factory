package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.core.service.DataValueHandler;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * description: DataGenerateDefaultServiceImpl <br>
 * date: 2021/1/17 20:24 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 处理属性自带的默认值
 * 包括属性依赖关系默认值处理
 */
@Service(value = "dataGenerateDefaultServiceImpl")
public class DataGenerateDefaultServiceImpl implements DataGenerateService {

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataBuildRequestFieldBO dataBuildRequestFieldBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();
        if (CollectionUtils.isNotEmpty(dataBuildRequestFieldBO.getDefaultValueList())) {
            int index = dataBuildRequestFieldBO.getDefaultValueList().size();
            return dataBuildRequestFieldBO.getDefaultValueList().get(dataSourceFieldRequestBean.getCurrentIndex() % index);
        }
        return null;
    }
}
