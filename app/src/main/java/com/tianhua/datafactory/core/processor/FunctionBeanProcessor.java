package com.tianhua.datafactory.core.processor;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.core.context.DataSourceFunctionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: spring bean后置处理器,扫描元数据
 * date020/10/14
 *
 * @author fanchunai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Component
public class FunctionBeanProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 类路径容器
     */
    private Map<String, String> map = new HashMap<>();

    @Resource
    private DataSourceFunctionContext dataSourceFunctionContext;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String clazzPath = bean.getClass().getCanonicalName();
        map.put(beanName,clazzPath);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = null;
        DataSourceFunction annotation = bean.getClass().getAnnotation(DataSourceFunction.class);
        if(annotation != null){
            dataSourceFunctionContext.putFunctionBean(annotation.dataSourceCode(), beanName);
        }
        try {
            //可能需要优化
            String classPath = map.get(beanName);
            logger.info("classPath = "+classPath);
            if (StringUtils.isNotEmpty(classPath)) {
                try {
                    Class<?> tClass = Class.forName(classPath);
                    if (tClass != null) {
                        methods = tClass.getDeclaredMethods();
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("class not found......");
                }
            }
            if (methods != null) {
                for (Method method : methods) {
                    DataSourceFunction dataSourceFunctionMethod = AnnotationUtils.findAnnotation(method, DataSourceFunction.class);
                    if (null != dataSourceFunctionMethod) {
                        dataSourceFunctionContext.putFunctionBean(dataSourceFunctionMethod.dataSourceCode(), beanName);
                    }
                }
            }
        }catch (Exception e){
            logger.error("function bind error",e);
        }


        return bean;
    }

    /**
     * 获取key
     *
     * @param classPath
     * @param method
     * @return
     */
    private String getKey(String classPath, Method method) {
        StringBuilder builder = new StringBuilder();
        builder.append(classPath + "." + method.getName() + "(");
        Class<?>[] clazzParamType = method.getParameterTypes();
        if (clazzParamType != null) {
            for (Class<?> clazz : clazzParamType) {
                String typeName = clazz.getSimpleName();
                builder.append(typeName + ",");
            }
        }
        String key = builder.toString();
        if (key.endsWith("(")) {
            return builder.append(")").toString();
        }
        return key.substring(0, key.length() - 1) + ")";
    }
}
