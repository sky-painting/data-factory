package com.tianhua.datafactory.domain.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements BeanFactoryAware {

    private static BeanFactory springBeanFactory;

    @Override
    public synchronized void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        springBeanFactory = beanFactory;
    }

    public synchronized static <T> T getBean(Class<? extends T> klass) {
        return springBeanFactory.getBean(klass);
    }
    
    public synchronized static Object getBean(String beanName) {
        return springBeanFactory.getBean(beanName);
    }
}