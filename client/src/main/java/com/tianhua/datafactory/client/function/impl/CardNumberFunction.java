package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 陈小哥cw
 * @date 2020/12/22 8:55
 * 参考
 */
@Service(value = "cardNumberFunction")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CARD_NUMBER)
public class CardNumberFunction implements CacheFunction {
    private static SecureRandom random = new SecureRandom();

    //todo 改造为caffine缓存

    private static List list = new ArrayList<>();
    private static Integer count = 100000;

    // 18位身份证号码各位的含义:
    // 1-2位省、自治区、直辖市代码；
    // 3-4位地级市、盟、自治州代码；
    // 5-6位县、县级市、区代码；
    // 7-14位出生年月日，比如19670401代表1967年4月1日；
    // 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数；
    // 18位为校验码，0-9和X。
    // 作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，
    // 计算的结果是0-10，如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，
    // 因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10
    public static void main(String[] args) {
       // String randomID = CardNumberFunction.getRandomID();
        //System.out.println(randomID);
        //System.out.println(randomID.substring(16, 17));
    }

    /**
     * 获取一个随机生成的身份证号码
     */
    public  String cardNumber() {
        Random random = new SecureRandom();

        String id = "421022199703149999";
        // 随机生成省、自治区、直辖市代码 1-2
        String provinces[] = {"11", "12", "13", "14", "15", "21", "22", "23",
                "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
                "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
                "63", "64", "65", "71", "81", "82"};
        String province = randomOne(provinces);
        // 随机生成地级市、盟、自治州代码 3-4
        String city = randomCityCode(18);
        // 随机生成县、县级市、区代码 5-6
        String county = randomCityCode(28);
        // 随机生成出生年月 7-14
        String birth = randomBirth(20, 50);
        // 随机生成顺序号 15-17(随机性别)
        String no = random.nextInt(899) + 100 + "";
        // 随机生成校验码 18
        String checks[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "X"};
        String check = randomOne(checks);
        // 拼接身份证号码
        id = province + city + county + birth + no + check;
        return id;
    }

    /**
     * 从String[] 数组中随机取出其中一个String字符串
     */
    private static String randomOne(String s[]) {
        Random random = new SecureRandom();
        return s[random.nextInt(s.length - 1)];
    }

    /**
     * 随机生成两位数的字符串（01-max）,不足两位的前面补0
     */
    private static String randomCityCode(int max) {
        Random random = new SecureRandom();
        int i = random.nextInt(max) + 1;
        return i > 9 ? i + "" : "0" + i;
    }

    /**
     * 随机生成minAge到maxAge年龄段的人的生日日期
     *
     * @param minAge
     * @param maxAge
     */
    private static String randomBirth(int minAge, int maxAge) {
        Random random = new SecureRandom();

        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());// 设置当前日期
        // 随机设置日期为前maxAge年到前minAge年的任意一天
        int randomDay = 365 * minAge
                + random.nextInt(365 * (maxAge - minAge));
        date.set(Calendar.DATE, date.get(Calendar.DATE) - randomDay);
        return dft.format(date.getTime());
    }

    @Override
    public String createOneData(String ... params) {
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
            list.add(cardNumber());
        }
        return list;
    }
}
