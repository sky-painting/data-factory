package com.coderman.tianhua.datafactory.client.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class CacheService {
    /**
     * 初始化缓存，key:对应的配置文件名
     * value:对应的配置内容列表
     */
    private static final Cache<String, List<String>> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    /**
     * 设置缓存数据
     * @param fileName
     * @param dataList
     */
    public void putCache(String fileName,List<String> dataList){
        manualCache.put(fileName,dataList);
    }

    /**
     * 获取缓存数据
     * @param fileName 文件名
     * @return
     */
    public List<String> getCache(String fileName){
        return manualCache.getIfPresent(fileName);
    }

}
