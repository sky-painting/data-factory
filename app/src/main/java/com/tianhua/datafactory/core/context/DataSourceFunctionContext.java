package com.tianhua.datafactory.core.context;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description:
 * date: 2021/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Component
public class DataSourceFunctionContext {

    private static ConcurrentMap<String,String> functionBeanMap = new ConcurrentHashMap<>();

    /**
     * 获取datasourcecode对应的function bean
     * @param dataSourceCode
     * @return
     */
    public String getBeanName(String dataSourceCode){
        return functionBeanMap.get(dataSourceCode);
    }

    /**
     * 将datasourcecode与function关系绑定
     * @param dataSourceCode
     * @param functionBeanName
     */
    public void putFunctionBean(String dataSourceCode,String functionBeanName){
        functionBeanMap.put(dataSourceCode, functionBeanName);
    }


}
