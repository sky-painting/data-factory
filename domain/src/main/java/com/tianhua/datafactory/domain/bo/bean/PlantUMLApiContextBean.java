package com.tianhua.datafactory.domain.bo.bean;

import com.coderman.utils.kvpair.KVPair;
import com.tianhua.datafactory.domain.bo.EnumBean;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2022/4/7
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class PlantUMLApiContextBean {

    /**
     * 目标项目的项目编码
     */
    private String projectCode;


    /**
     * api模型列表
     */
    private List<ApiBO> apiBeanList = new ArrayList<>();


    private List<ParamModelBO>  paramClassBeanList = new ArrayList<>();


    private Map<String, EnumBean> enumBeanMap = new HashMap<>();


    /**
     * 添加API-Bean
     * @param apiBean
     */
    public void addApiBean(ApiBO apiBean){
        apiBeanList.add(apiBean);
    }

    /**
     * 添加API-Bean
     * @param apiBeanList
     */
    public void addBatchApiBean(List<ApiBO> apiBeanList){
        if(apiBeanList == null || apiBeanList.isEmpty()){
            return;
        }

        for (ApiBO apiBO : apiBeanList){
            addApiBean(apiBO);
        }
    }

    public void addParamClassBean(ParamModelBO paramModelBO){
        this.paramClassBeanList.add(paramModelBO);
    }



    public void addEnumBean(EnumBean enumBean) {
        this.enumBeanMap.put(enumBean.getClassName(), enumBean);
    }


}
