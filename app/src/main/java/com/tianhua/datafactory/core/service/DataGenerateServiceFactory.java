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


    @Resource(name = "dataGenerateDubboImpl")
    private DataGenerateService dataGenerateDubboImpl;

    @Resource(name = "dataGenerateLocalKVImpl")
    private DataGenerateService dataGenerateLocalKVImpl;


    @Resource(name = "dataGenerateFileDataServiceImpl")
    private DataGenerateService dataGenerateFileDataServiceImpl;

    @Resource(name = "dataGenerateHttpApiServiceImpl")
    private DataGenerateService dataGenerateHttpApiServiceImpl;

    /**
     * 根据类型动态获取数据生成bean
     * @param dataSouceType
     * @return
     * @throws Exception
     */
    public DataGenerateService getDataGenerateService(int dataSouceType) throws Exception {
        if(DataSourceTypeEnum.FROM_SERVICE_ENUM.getCode() == dataSouceType){
            return dataGenerateLocalKVImpl;
        }

        //属性上直接设置的默认值列表
        if(DataSourceTypeEnum.FIELD_DEFAULT.getCode() == dataSouceType){
            return dataGenerateDefaultServiceImpl;
        }

        //datafactory内置提供的函数式随机值生成服务
        if(DataSourceTypeEnum.FUNCTION_DATASOURCE.getCode() == dataSouceType){
            return dataGenerateFunctionServiceImpl;
        }
        
        
        //从dubbo远程接口中获取随机数据
        if(DataSourceTypeEnum.FROM_DUBBO.getCode() == dataSouceType){
            return dataGenerateDubboImpl;
        }
        
        //从文件中获取数据
        if(DataSourceTypeEnum.FROM_FILE_DATA.getCode() == dataSouceType){
            return dataGenerateFileDataServiceImpl;
        }

        //从http api中获取数据
        if(DataSourceTypeEnum.FROM_SERVICE_API_HTTP.getCode() == dataSouceType){
            return dataGenerateHttpApiServiceImpl;
        }

        
        throw new Exception("不支持的数据源类型");

    }


}
