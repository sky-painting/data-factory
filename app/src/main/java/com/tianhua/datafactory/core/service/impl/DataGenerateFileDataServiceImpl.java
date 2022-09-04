package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.service.FileDataService;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 * 从文件数据源中获取数据源
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateFileDataServiceImpl")
public class DataGenerateFileDataServiceImpl implements DataGenerateService {

    @Autowired
    private FileDataService fileDataService;

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        String dataSourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();
        if(!dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode + dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getFieldName();
        }
        return fileDataService.getFileData(dataSourceCode);
    }


}
