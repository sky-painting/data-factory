package com.tianhua.datafactory.client.function.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "chineseNameFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CHINESE_NAME)
public class ChineseNameFunc   implements CacheFunction {
    @Autowired
    private FileDataService fileDataService;
    private static SecureRandom random = new SecureRandom();

    private static List list = new ArrayList<>();
    private static Integer count = 100000;
    @Override
    public String createOneData(String... params) throws Exception {
        if(list.isEmpty()){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();
    }

    @Override
    public synchronized void buildCache(Integer count) throws Exception {
        this.count = count;
        list = initCache(count);
    }

    @Override
    public synchronized void clearCache() {
        list.clear();
    }

    private List  initCache(Integer count) throws Exception {
        List<Object> firstNameList = fileDataService.getFileDataList(FileDataEnums.FIRST_NAME.getFileName());
        List<Object> lastNameList = fileDataService.getFileDataList(FileDataEnums.LAST_NAME.getFileName());
        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            String chineseName = firstNameList.get(random.nextInt(firstNameList.size())).toString() + lastNameList.get(random.nextInt(lastNameList.size())).toString();
            list.add(chineseName);
        }
        return list;
    }
}
