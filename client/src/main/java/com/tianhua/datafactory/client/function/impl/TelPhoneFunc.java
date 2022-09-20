package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * 获取电话号码
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "telPhoneFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.TEL_PHONE)
public class TelPhoneFunc implements CacheFunction {
    /**
     * 返回手机号码
     */
    private static String[] telFirst = "130,134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,175,176,177,180,182,183,188,189,191,186".split(",");
    private static SecureRandom random = new SecureRandom();
    //todo 改造为caffine缓存
    private static List list = new ArrayList<>();
    private static Integer count = 100000;

    @Override
    public String createOneData(String... params) {
        if(list.isEmpty()){
            buildCache(count);
        }
        return list.get(random.nextInt(list.size())).toString();

    }

    private  int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }


    @Override
    public synchronized void buildCache(Integer count) {
        this.count = count;
        list = initCache(count);
    }


    private List  initCache(Integer count){
        List<String> list = new ArrayList<>(count);
        for (int i =0;i < count;i++){
            int index = getNum(0, telFirst.length - 1);
            String first = telFirst[index];
            String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
            String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
            list.add(first + second + third);
        }
        return list;
    }
}
