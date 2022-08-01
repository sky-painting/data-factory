package com.tianhua.datafactory.core.adapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/8/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class DubboServiceFactory {

    private ApplicationConfig application;
    private RegistryConfig registry;
    @Value("${dubbox.registry.address}")
    private String address;
    @Value("${dubbo.application.name}")
    private String name;

    private static class SingletonHolder {
        private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
    }

    private DubboServiceFactory() {
        try {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            name="dubbo-provider2";
            applicationConfig.setName(name);
            RegistryConfig registryConfig = new RegistryConfig();
            address= "zookeeper://127.0.0.1:2181";
            registryConfig.setAddress(address);
            this.application = applicationConfig;
            this.registry = registryConfig;
        } catch (Exception e) {
            log.error("DubboServiceFactory", e);
            e.printStackTrace();
        }


    }

    public static DubboServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters) {
        try {

            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            reference.setServices("dubbo-provider");
            reference.setApplication(application);
            reference.setRegistry(registry);
            reference.setInterface(interfaceClass); // 接口名
            reference.setGeneric(true); // 声明为泛化接口

            //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
            //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
            //API方式编程时，容易忽略此问题。
            //这里使用dubbo内置的简单缓存工具类进行缓存

            ReferenceConfigCache cache = ReferenceConfigCache.getCache();
            GenericService genericService = cache.get(reference);

            int len = parameters.size();
            String[] invokeParamTyeps = new String[len];
            Object[] invokeParams = new Object[len];
            for (int i = 0; i < len; i++) {
                invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
                invokeParams[i] = parameters.get(i).get("Object");
            }
            return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
        } catch (Exception e) {
            log.error("genericInvoke", e);
            e.printStackTrace();
        }
        return "500";
    }


    public static void main(String[] args) {
        DubboServiceFactory dubbo = DubboServiceFactory.getInstance();
        String interfaceName = "com.shenshuai.dubbo.client.HelloWorld";
        String methodName = "sayHello";
        List<Map<String, Object>> parameters = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ParamType","java.lang.String");
        map.put("Object","shenshuai");
        parameters.add(map);
        Object result =  dubbo.genericInvoke(interfaceName, methodName, parameters);

    }

}
