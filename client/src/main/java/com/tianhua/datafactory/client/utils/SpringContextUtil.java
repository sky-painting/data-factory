package com.tianhua.datafactory.client.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author fanchunshuai
 * @Date 2019/11/18 00
 * @Description:
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(Class T) {
        try {
            return applicationContext.getBean(T);
        } catch (BeansException e) {
            return null;
        }
    }

    public static Object getBean(String name) {
        try {
            return applicationContext.getBean(name);

        } catch (BeansException e) {
            return null;
        }
    }


    public static <T> List<T> getBeanOfType(Class T){
        Map<String,T> beanMap = applicationContext.getBeansOfType(T);
        if(beanMap == null){
            return null;
        }
        return beanMap.values().stream().collect(Collectors.toList());
    }



}