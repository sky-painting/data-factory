package com.tianhua.datafactory.core.service;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description
 * date: 2022/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class FieldValueFactory {

    @Resource(name = "dataGenerateDefaultServiceImpl")
    private DataGenerateService dataGenerateDefaultServiceImpl;


    @Resource(name = "dataGenerateDubboImpl")
    private DataGenerateService dataGenerateDubboImpl;

    @Resource(name = "dataGenerateFunctionServiceImpl")
    private DataGenerateService dataGenerateFunctionServiceImpl;

    @Resource(name = "dataGenerateLocalKVImpl")
    private DataGenerateService dataGenerateLocalKVImpl;


    @Resource(name = "dataGenerateFileDataServiceImpl")
    private DataGenerateService dataGenerateFileDataServiceImpl;



    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    public Object getFieldValue(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldBO dataBuildRequestFieldBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();

        String dataSourceCode = dataBuildRequestFieldBO.getDataSourceCode();

        //从默认值中获取数据
        if (StringUtils.isEmpty(dataSourceCode)
                || CollectionUtils.isNotEmpty(dataBuildRequestFieldBO.getDefaultValueList())) {
            return dataGenerateDefaultServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

        if(dataBuildRequestFieldBO.getDataSourceType() == null){
            log.warn("数据源类型为空,dataBuildRequestFieldBO = {}", JSON.toJSONString(dataBuildRequestFieldBO));
            return null;
        }

        int dataSourceType = dataBuildRequestFieldBO.getDataSourceType();

        //来自服务模型枚举
        if(DataSourceTypeEnum.FROM_SERVICE_ENUM.getCode() == dataSourceType){
            return dataGenerateLocalKVImpl.getRandomData(dataSourceFieldRequestBean);
        }

        //属性上直接设置的默认值列表
        if(DataSourceTypeEnum.FIELD_DEFAULT.getCode() == dataSourceType){
            return dataGenerateDefaultServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

        //datafactory内置提供的函数式随机值生成服务
        if(DataSourceTypeEnum.FUNCTION_DATASOURCE.getCode() == dataSourceType){
            return dataGenerateFunctionServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

        //从dubbo远程接口中获取随机数据
        if(DataSourceTypeEnum.FROM_DUBBO.getCode() == dataSourceType){

            return null;
            //todo 临时注释
           // return dataGenerateDubboImpl.getRandomData(dataSourceFieldRequestBean);
        }



        //从文件中获取数据
        if(DataSourceTypeEnum.FROM_FILE_DATA.getCode() == dataSourceType){
            dataGenerateFileDataServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

        return null;
    }

}
