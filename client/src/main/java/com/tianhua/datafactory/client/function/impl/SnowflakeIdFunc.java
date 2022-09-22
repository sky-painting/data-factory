package com.tianhua.datafactory.client.function.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.utils.SnowflakeIdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description
 * date: 2022/8/8
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "snowflakeIdFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.SNOWFLAKE_ID)
public class SnowflakeIdFunc implements CacheFunction {

    private static SecureRandom random = new SecureRandom();


    /**
     * caffine缓存
     */
    private static final Cache<String, List> manualCache = Caffeine.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(5*60, TimeUnit.SECONDS)
            .build();


    private  Integer count = 10000;
    @Override
    public  String createOneData(String... params) {

        List list = manualCache.getIfPresent(InnerDataSourceCode.SNOWFLAKE_ID);

        if(CollectionUtils.isEmpty(list)){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    @Override
    public synchronized void buildCache(Integer count) {
        this.count = count;
        initCache(count);
    }


    private void   initCache(Integer count){

        if(manualCache.getIfPresent(InnerDataSourceCode.SNOWFLAKE_ID) != null){
            return;
        }
        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            list.add(SnowflakeIdWorker.getNextId());
        }
        manualCache.put(InnerDataSourceCode.SNOWFLAKE_ID, list);
    }
}
