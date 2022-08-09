package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;

import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.*;

/**
 * description: DataFactoryServiceImpl <br>
 * date: 2020/12/5 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class DataFactoryServiceImpl implements DataFactoryService {

    private ThreadLocal<SecureRandom> randomThreadLocal = new ThreadLocal<>();


    @Autowired
    private FunctionFactory functionFactory;


    @Resource(name = "dataGenerateDefaultServiceImpl")
    private DataGenerateService dataGenerateDefaultServiceImpl;


    @Resource(name = "dataGenerateDubboImpl")
    private DataGenerateService dataGenerateDubboImpl;

    @Resource(name = "dataGenerateFunctionServiceImpl")
    private DataGenerateService dataGenerateFunctionServiceImpl;

    @Resource(name = "dataGenerateLocalKVImpl")
    private DataGenerateService dataGenerateLocalKVImpl;


    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;


    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object getFieldValue(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldBO dataBuildRequestFieldBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();

        String dataSourceCode = dataBuildRequestFieldBO.getDataSourceCode();
        //从默认值中获取数据
        if (StringUtils.isEmpty(dataSourceCode) || CollectionUtils.isNotEmpty(dataBuildRequestFieldBO.getDefaultValueList())) {
            return dataGenerateDefaultServiceImpl.getRandomData(dataSourceFieldRequestBean);
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
            return dataGenerateDubboImpl.getRandomData(dataSourceFieldRequestBean);
        }

        return null;

    }

    @Override
    public ResultDataDto<List<Map<String, Object>>> generateData(DataBuildRequestBO dataBuildRequestBO) throws Exception {
        randomThreadLocal.set(new SecureRandom());
        bindDataSource(dataBuildRequestBO);

        List<DataBuildRequestFieldBO> dataFactoryRequestFieldBeanList = dataBuildRequestBO.getFieldBOList();


        List<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount() * 2);
        ResultDataDto resultDataDto = new ResultDataDto();

        Map<String, Function> functionMap = new HashMap<>();

        for (DataBuildRequestFieldBO dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {
            String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
            if (StringUtils.isNotBlank(dataSourceCode) && dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
                Function function = functionFactory.createFunction(dataSourceCode);
                functionMap.put(dataSourceCode,function);
            }
        }

        for (int i = 0; i < dataBuildRequestBO.getBuildCount(); i++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataFactoryRequestFieldBeanList.size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            dataSourceFieldRequestBean.setCurrentIndex(i);
            //如果有字段依赖可以进行排序
            for (DataBuildRequestFieldBO dataBuildRequestFieldBO : dataFactoryRequestFieldBeanList) {

                dataSourceFieldRequestBean.setFunction(functionMap.get(dataBuildRequestFieldBO.getDataSourceCode()));
                dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
                dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
                dataSourceFieldRequestBean.setRandom(randomThreadLocal.get());
                dataSourceFieldRequestBean.setVarDependencyMap(dataBuildRequestFieldBO.getVarDependencyMap());
                //获取随机字段值
                Object fieldValue = getFieldValue(dataSourceFieldRequestBean);
                fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), fieldValue);
            }
            batchResultList.add(fieldValueMap);
        }
        randomThreadLocal.remove();
        resultDataDto.setData(batchResultList);
        return resultDataDto;
    }

    @Override
    public String buildData(DataBuildRequestFieldBO dataBuildRequestFieldBO) {
        DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
        dataSourceFieldRequestBean.setFunction(dataBuildRequestFieldBO.getFunction());

        String dataSourceCode = dataBuildRequestFieldBO.getDataSourceCode();
        if(dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode.split("#")[0];
        }
        DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataSourceCode);
        dataBuildRequestFieldBO.setDataSourceType(dataSourceBO.getSourceType());
        dataBuildRequestFieldBO.setDataSourceBO(dataSourceBO);
        dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
        Object object = null;
        try {
            object = getFieldValue(dataSourceFieldRequestBean);
            if(object == null){
                return "";
            }
            return object.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 获取数据源详情
     * @param dataBuildRequestBO
     */
    private void bindDataSource(DataBuildRequestBO dataBuildRequestBO){
        List<DataBuildRequestFieldBO> dataBuildRequestFieldBeans = dataBuildRequestBO.getFieldBOList();
        for (DataBuildRequestFieldBO dataBuildRequestFieldBean  : dataBuildRequestFieldBeans){
            if(StringUtils.isEmpty(dataBuildRequestFieldBean.getDataSourceCode())){
                continue;
            }
            DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataBuildRequestFieldBean.getDataSourceCode());
            dataBuildRequestFieldBean.setDataSourceType(dataSourceBO.getSourceType());
            dataBuildRequestFieldBean.setDataSourceBO(dataSourceBO);
        }
    }

}
