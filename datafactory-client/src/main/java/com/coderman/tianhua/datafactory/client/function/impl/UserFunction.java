package com.coderman.tianhua.datafactory.client.function.impl;


import com.coderman.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.coderman.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.coderman.tianhua.datafactory.client.enums.FileDataEnums;
import com.coderman.tianhua.datafactory.client.function.Function;
import com.coderman.tianhua.datafactory.client.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * description: UserFunction <br>
 * date: 2020/12/15 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 用户相关的数据工厂工具类生成
 */
@Service
public class UserFunction implements Function<String> {

    @Autowired
    private FileDataService fileDataService;
    private static SecureRandom random = new SecureRandom();

    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    private static final String[] EMAIL_SUFFIX = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

    private static final int MAX_YEAR = 2030;
    private static final int MIN_YEAR = 1949;
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;
    private static final int MAX_DAY = 1;
    private static final int MIN_DAY = 28;

    /**
     * 获取一个电话号码
     *
     * @return
     */
    @DataSourceFunction(dataSourceCode = InnerDataSourceCode.TEL_PHONE)
    public  String tel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }


    /**
     * 获取一个日期
     *
     * @return
     */
    @DataSourceFunction(dataSourceCode = InnerDataSourceCode.DATE)
    public  Date date() {
        int year = random.nextInt(MAX_YEAR) % (MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;
        int month = random.nextInt(MAX_MONTH) % (MAX_MONTH - MIN_MONTH + 1) + MIN_MONTH;
        int day = random.nextInt(MAX_DAY) % (MAX_DAY - MIN_DAY + 1) + MIN_DAY;
        return localDate2Date(LocalDate.of(year, month, day));
    }

    /**
     * 获取一个邮箱----邮箱函数需要重新定义
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public  String email(int lMin, int lMax) {
        int length = getNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * BASE_CHAR.length());
            sb.append(BASE_CHAR.charAt(number));
        }
        sb.append(EMAIL_SUFFIX[(int) (Math.random() * EMAIL_SUFFIX.length)]);
        return sb.toString();
    }

    /**
     * 获取一个指定位数的随机数--可以当做密码，or验证码
     * @param len
     * @return
     */
    @DataSourceFunction(dataSourceCode = InnerDataSourceCode.RANDOM)
    public  String random(int len) {
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }


    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    private  Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    @DataSourceFunction(dataSourceCode = InnerDataSourceCode.CHINESE_NAME)
    private String chineseName(){
        List<String> firstNameList = fileDataService.getFileDataList(FileDataEnums.FIRST_NAME.getFileName());
        List<String> lastNameList = fileDataService.getFileDataList(FileDataEnums.LAST_NAME.getFileName());
        return firstNameList.get(random.nextInt(firstNameList.size())) + lastNameList.get(random.nextInt(lastNameList.size()));
    }


    private  int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    @Override
    public String createOneData(String methodName, String... params) {
        if(methodName.equals("tel")){
            return tel();
        }else if(methodName.equals("chineseName")){
            return chineseName();
        }else if(methodName.equals("random")){
            int length = Integer.parseInt(params[0]);
            return random(length);
        }
        return null;
    }
}
