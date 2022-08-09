package com.tianhua.datafactory.core.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.ability.KVPairService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Description
 * 对接本地kv enum
 * date: 2022/7/31
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateLocalKVImpl")
@Slf4j
public class DataGenerateLocalKVImpl implements DataGenerateService {
    @Autowired
    private KVPairService kvPairService;

    public Random random = new SecureRandom();

    private Cache<String,List> cache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .maximumSize(5000)
            .build();


    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        String sourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();
        List list = cache.get(sourceCode,code -> initCache(sourceCode));
        return list.get(random.nextInt(list.size()));
    }

    private List  initCache(String dataSourceCode){
        if(!dataSourceCode.contains("#")){
            log.error("本地kv对应的sourceCode格式错误.");
            //todo throw exception
        }
        String [] array = dataSourceCode.split("#");

        List<KVPairBO> list = kvPairService.getList(KVPairBO.instance().groupKey(array[0]).Key(array[1]));
        if(CollectionUtils.isEmpty(list)){
            //todo throw exception
            return null;
        }
        return list.stream().map(KVPairBO::getValue).collect(Collectors.toList());
    }
}
