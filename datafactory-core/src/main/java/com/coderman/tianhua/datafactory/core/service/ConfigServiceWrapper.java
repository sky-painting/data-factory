package com.coderman.tianhua.datafactory.core.service;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * description: ConfigServiceWrapper <br>
 * date: 2020/12/10 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class ConfigServiceWrapper {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    private ConfigService configService;
    /**
     * 通过原生Java方式获取ConfigService
     * @return
     * @throws NacosException
     */
    public synchronized ConfigService getConfigService() throws NacosException {
        if(configService == null){
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, nacosDiscoveryProperties.getServerAddr());
            configService = NacosFactory.createConfigService(properties);
        }
        return configService;
    }

    /**
     * 获取nacos配置
     * @param dataId
     * @param groupId
     * @return
     */
    public String getConfig(String dataId,String groupId){
        ConfigService configService = null;
        try {
            configService = this.getConfigService();
            //默认3秒
            return configService.getConfig(dataId,groupId,3000);

        } catch (NacosException e) {
            log.error("链接nacos失败!",e);
        }
        return null;
    }
}
