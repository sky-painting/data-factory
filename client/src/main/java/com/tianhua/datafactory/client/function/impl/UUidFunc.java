package com.tianhua.datafactory.client.function.impl;

import cn.hutool.core.lang.UUID;
import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    private static List list = new ArrayList<>();
    private static Integer count = 100000;

    @Override
    public synchronized void buildCache(Integer count) throws Exception {
        this.count = count;
        list = initCache(count);
    }

    @Override
    public void clearCache() {
        if(list.isEmpty()){
            return;
        }
        list.clear();
    }

    @Override
    public Object createOneData(String... params) throws Exception {
        if(list.isEmpty()){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    private List  initCache(Integer count){
        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            list.add(UUID.fastUUID().toString());
        }
        return list;
    }
}
