package com.tianhua.datafactory.client.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * date: 2021/1/15
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class KVCacheService {

    private static SecureRandom random = new SecureRandom();
    /**
     * 初始化缓存，key:dataSourceCode数据源编码
     * value:对应的数据源值列表
     */
    private static final Cache<String, List<Object>> manualCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10*60,TimeUnit.SECONDS)
            .build();

    /**
     * 设置缓存数据
     * @param dataSourceCode
     * @param dataList
     */
    public void putCache(String dataSourceCode,List<Object> dataList){
        manualCache.put(dataSourceCode,dataList);
    }

    /**
     * 获取缓存数据
     * @param dataSourceCode 数据源编码
     * @return
     */
    public List<Object> getCache(String dataSourceCode){
        return manualCache.getIfPresent(dataSourceCode);
    }



    /**
     * 获取缓存数据
     * @param dataSourceCode 数据源编码
     * @return
     */
    public Object getCacheOne(String dataSourceCode){
        List<Object> dataList = getCache(dataSourceCode);
        return dataList.get(random.nextInt(dataList.size()));
    }
}
