package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * 支持spring cloud的接口api
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateSpringCloudServiceImpl")
@Slf4j
public class DataGenerateSpringCloudServiceImpl implements DataGenerateService {
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        return null;
    }
}
