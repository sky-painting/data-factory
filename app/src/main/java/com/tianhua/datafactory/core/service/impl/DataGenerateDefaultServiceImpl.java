package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.core.service.DataValueHandler;
import com.tianhua.datafactory.domain.bo.DataBuildRequestFieldBean;
import com.tianhua.datafactory.domain.bo.DataSourceFieldRequestBean;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private DataValueHandler dataValueHandler;
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataBuildRequestFieldBean dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean();

        if (CollectionUtils.isNotEmpty(dataFactoryRequestFieldBean.getDefaultValueList())) {
            int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
            Object value = dataFactoryRequestFieldBean.getDefaultValueList().get(secureRandom.nextInt(size));
            Map<String, List<String>> varDependencyMap = dataSourceFieldRequestBean.getVarDependencyMap();
            if(varDependencyMap == null || varDependencyMap.isEmpty()){
                return dataValueHandler.handleValue(value,dataFactoryRequestFieldBean.getDataFactoryRequestFieldRuleBean());
            }
            String key = dataFactoryRequestFieldBean.getFieldName()+"-"+value.toString();
            List<String> dependencyList = varDependencyMap.get(key);
            if(CollectionUtils.isNotEmpty(dependencyList)){
                Map<String,List<String>>  kvDependencyMap = new HashMap<>();
                //对依赖方进行处理
                dependencyList.forEach(dependencyValue->{
                    String [] kvArr = dependencyValue.split("-");

                    List<String> valueList = kvDependencyMap.get(kvArr[0]);
                    if(valueList == null){
                        valueList = new ArrayList<>();
                    }
                    valueList.add(kvArr[1]);
                    kvDependencyMap.put(key, valueList);
                });

                //依赖方按当前被依赖方的值确定自己的值
                kvDependencyMap.forEach((k,vList)->{
                    dataSourceFieldRequestBean.getFieldValueMap().put(key, vList.get(secureRandom.nextInt(vList.size())));
                });
            }

            return value;
        }
        return null;
    }
}
