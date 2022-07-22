package com.tianhua.datafactory.domain.bo;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Description:
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class PlantUmlDomainContextBean {

    private Map<String, ClassBean> classBeanMap = new HashMap<>();

    private Map<String, EnumBean> enumBeanMap = new HashMap<>();

    private Map<String, InterfaceBean> interfaceBeanMap = new HashMap<>();


    /**
     * 派生类上下文
     */
    private PlantUmlDomainContextBean derivedPlantUmlContextBean;


    /**
     * app应用名称
     */
    private String appName;



    public void addClassBean(ClassBean classBean) {
        this.classBeanMap.put(classBean.getClassName(), classBean);
    }

    public void addInterfaceBean(InterfaceBean interfaceBean) {

        this.interfaceBeanMap.put(interfaceBean.getClassName(), interfaceBean);
    }


    public void addEnumBean(EnumBean enumBean) {
        this.enumBeanMap.put(enumBean.getClassName(), enumBean);
    }


}
