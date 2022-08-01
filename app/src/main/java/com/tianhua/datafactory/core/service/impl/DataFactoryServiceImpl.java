package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;

import com.tianhua.datafactory.core.functionfactory.FunctionFactory;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.DataBuildRequestBean;
import com.tianhua.datafactory.domain.bo.DataBuildRequestFieldBean;
import com.tianhua.datafactory.domain.bo.DataBuildRequestFieldRuleBean;
import com.tianhua.datafactory.domain.bo.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
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


    @Resource(name = "dataGenerateFunctionServiceImpl")
    private DataGenerateService dataGenerateFunctionServiceImpl;


    @Resource(name = "dataGenerateRemoteServiceImpl")
    private DataGenerateService dataGenerateRemoteServiceImpl;


    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;


    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object getFieldValue(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldBean dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean();

        String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
        //从默认值中获取数据
        if (StringUtils.isEmpty(dataSourceCode) || CollectionUtils.isNotEmpty(dataFactoryRequestFieldBean.getDefaultValueList())) {
            return dataGenerateDefaultServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }
        //如果是内置数据源则从内置数据源工厂中的Function中构建随机数据
        if (dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
            return dataGenerateFunctionServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }else {
            //从远程数据源获取随机数据
            return dataGenerateRemoteServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

    }

    @Override
    public ResultDataDto generateData(DataBuildRequestBean dataFactoryRequestBean) throws Exception {
        randomThreadLocal.set(new SecureRandom());
        buildType(dataFactoryRequestBean);

        List<DataBuildRequestFieldBean> dataFactoryRequestFieldBeanList = dataFactoryRequestBean.getDataFactoryRequestFieldBeanList();


        List<Map<String, Object>> batchResultList = new ArrayList<>(dataFactoryRequestBean.getGenerateCount() * 2);
        ResultDataDto resultDataDto = new ResultDataDto();

        Map<String, Function> functionMap = new HashMap<>();

        for (DataBuildRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {
            String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
            if (StringUtils.isNotBlank(dataSourceCode) && dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
                Function function = functionFactory.createFunction(dataSourceCode);
                functionMap.put(dataSourceCode,function);
            }
        }

        for (int i = 0; i < dataFactoryRequestBean.getGenerateCount(); i++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataFactoryRequestFieldBeanList.size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            //如果有字段依赖可以进行排序
            for (DataBuildRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {

                dataSourceFieldRequestBean.setFunction(functionMap.get(dataFactoryRequestFieldBean.getDataSourceCode()));
                dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
                dataSourceFieldRequestBean.setDataFactoryRequestFieldBean(dataFactoryRequestFieldBean);
                dataSourceFieldRequestBean.setRandom(randomThreadLocal.get());
                dataSourceFieldRequestBean.setVarDependencyMap(dataFactoryRequestFieldBean.getVarDependencyMap());
                //获取随机字段值
                Object fieldValue = getFieldValue(dataSourceFieldRequestBean);
                fieldValueMap.put(dataFactoryRequestFieldBean.getFieldName(), fieldValue);
            }

            batchResultList.add(fieldValueMap);
        }
        resultDataDto.setData(batchResultList);
        return resultDataDto;
    }

    @Override
    public String buildData(String dataSourceCode) {

        return null;
    }

    /**
     * 对随机数进行后置处理
     *
     * @param factoryRequestFieldBean
     * @param randomValue
     * @return
     */
    private String getPostValueString(DataBuildRequestFieldBean factoryRequestFieldBean, Object randomValue) {
        DataBuildRequestFieldRuleBean dataFactoryRequestFieldRuleBean2 = factoryRequestFieldBean.getDataFactoryRequestFieldRuleBean();
        String value = randomValue.toString();
        //字符串型随机数据的后置处理
        if (factoryRequestFieldBean.getFieldTypeStr().equals("String")) {
            if (StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean2.getPrefixStr())) {
                value = dataFactoryRequestFieldRuleBean2.getPrefixStr() + value;
            }
            if (StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean2.getSubfixStr())) {
                value = value + dataFactoryRequestFieldRuleBean2.getSubfixStr();
            }
        }
        return value;
    }



    private void buildType(DataBuildRequestBean dataFactoryRequestBean){
        List<DataBuildRequestFieldBean> dataBuildRequestFieldBeans = dataFactoryRequestBean.getDataFactoryRequestFieldBeanList();
        for (DataBuildRequestFieldBean dataBuildRequestFieldBean  : dataBuildRequestFieldBeans){
            DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataBuildRequestFieldBean.getDataSourceCode());
            dataBuildRequestFieldBean.setDataSourceType(dataSourceBO.getSourceType());
        }
    }

}
