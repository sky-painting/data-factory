package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.service.DataGenerateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * description: DataGenerateDefaultServiceImpl <br>
 * date: 2021/1/17 20:24 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateDefaultServiceImpl")
public class DataGenerateDefaultServiceImpl implements DataGenerateService {
    private static SecureRandom secureRandom = new SecureRandom();
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataFactoryRequestFieldBean dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean();
        if (CollectionUtils.isNotEmpty(dataFactoryRequestFieldBean.getDefaultValueList())) {
            int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
            return dataFactoryRequestFieldBean.getDefaultValueList().get(secureRandom.nextInt(size));
        } else {
            //todo 寻找依赖方  支持一对多数据字段存在依赖的情况下的数据生成
        }
        return null;
    }
}
