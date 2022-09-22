package com.tianhua.datafactory.client.function.impl;

import cn.hutool.core.lang.UUID;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.CacheFunction;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description
 * date: 2022/9/18
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "uUidFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.UUID)
public class UUidFunc  implements CacheFunction {
    private static SecureRandom random = new SecureRandom();

    private Integer count = 10000;

    /**
     * caffine缓存
     */
    private static final Cache<String, List> manualCache = Caffeine.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(5*60, TimeUnit.SECONDS)
            .build();



    @Override
    public synchronized void buildCache(Integer count) throws Exception {
        this.count = count;
        initCache(count);
    }

    @Override
    public Object createOneData(String... params) throws Exception {

        List list = manualCache.getIfPresent(InnerDataSourceCode.UUID);

        if(CollectionUtils.isEmpty(list)){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    private void   initCache(Integer count){
        if(manualCache.getIfPresent(InnerDataSourceCode.UUID) != null){
            return;
        }

        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            list.add(UUID.fastUUID().toString());
        }
        manualCache.put(InnerDataSourceCode.UUID, list);
    }
}
