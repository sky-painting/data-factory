package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.service.FileDataService;
import com.tianhua.datafactory.client.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    //todo 改造为caffine缓存
    private static List list = new ArrayList<>();
    private static Integer count = 100000;
    @Override
    public  String createOneData(String... params) {
        if(list.isEmpty()){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    @Override
    public synchronized void buildCache(Integer count) {
        this.count = count;
        list = initCache(count);
    }


    private List  initCache(Integer count){
        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            list.add(SnowflakeIdWorker.getNextId());
        }
        return list;
    }
}
