package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.core.service.DataValueHandler;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
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
    private static SecureRandom secureRandom = new SecureRandom();

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataBuildRequestFieldBO dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();
        if (CollectionUtils.isNotEmpty(dataFactoryRequestFieldBean.getDefaultValueList())) {
            int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
            return dataFactoryRequestFieldBean.getDefaultValueList().get(secureRandom.nextInt(size));
        }
        return null;
    }
}
