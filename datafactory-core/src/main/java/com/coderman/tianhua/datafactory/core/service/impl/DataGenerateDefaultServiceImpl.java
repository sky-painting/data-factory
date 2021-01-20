package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.kvpair.KVPair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            Object value = dataFactoryRequestFieldBean.getDefaultValueList().get(secureRandom.nextInt(size));
            Map<String, List<String>> varDependencyMap = dataSourceFieldRequestBean.getVarDependencyMap();
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
