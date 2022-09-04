package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description
 * date: 2022/7/31
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class DataGenerateServiceFactory {

    @Resource(name = "dataGenerateDefaultServiceImpl")
    private DataGenerateService dataGenerateDefaultServiceImpl;


    @Resource(name = "dataGenerateFunctionServiceImpl")
    private DataGenerateService dataGenerateFunctionServiceImpl;



    public DataGenerateService getDataGenerateService(Integer dataSouceType){
        if(DataSourceTypeEnum.FROM_SERVICE_ENUM.getCode() == dataSouceType.intValue()){
            return dataGenerateDefaultServiceImpl;
        }
        if(DataSourceTypeEnum.FIELD_DEFAULT.getCode() == dataSouceType.intValue()){
            return dataGenerateFunctionServiceImpl;
        }

        if(DataSourceTypeEnum.FUNCTION_DATASOURCE.getCode() == dataSouceType.intValue()){
            return dataGenerateDefaultServiceImpl;
        }
        //todo throw exception not support;
        return null;

    }


}
