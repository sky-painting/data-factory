/*
package com.tianhua.datafactory.core.service;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.tianhua.datafactory.core.adaptor.NacosDataAdaptor;
import com.coderman.utils.kvpair.KVPair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

*/
/**
 * description: ConfigServiceWrapper nacos 服务
 * date: 2020/12/10 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 *//*

@Service
@Slf4j
public class ConfigServiceWrapper {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    private ConfigService configService;

    @Autowired
    private NacosDataAdaptor nacosDataAdaptor;
    */
/**
     * 通过原生Java方式获取ConfigService
     * @return
     * @throws NacosException
     *//*

    public synchronized ConfigService getConfigService() throws NacosException {
        if(configService == null){
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, nacosDiscoveryProperties.getServerAddr());
            configService = NacosFactory.createConfigService(properties);
        }
        return configService;
    }

    */
/**
     * 获取nacos配置
     * @param dataId
     * @param groupId
     * @return
     *//*

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

    */
/**
     * 对nacos content进行预处理
     * @param dataId
     * @param groupId
     * @return
     *//*

    public List<KVPair<String,String>> getConfigList(String dataId,String groupId){
        String content = getConfig(dataId, groupId);
        if(StringUtils.isEmpty(content)){
            //todo throw null exception
            return  null;
        }
        return nacosDataAdaptor.getNacosDataKV(content);
    }


    */
/**
     * 对nacos content进行预处理
     * @param dataId
     * @param groupId
     * @return
     *//*

    public List<Map<String,String>> getConfigList(String dataId, String groupId, String jsonTemplate){
        String content = getConfig(dataId, groupId);
        if(StringUtils.isEmpty(content)){
            //todo throw null exception
            return  null;
        }
        return nacosDataAdaptor.getNacosDataMap(content,jsonTemplate);
    }



}
*/
