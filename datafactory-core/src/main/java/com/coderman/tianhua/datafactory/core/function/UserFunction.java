package com.coderman.tianhua.datafactory.core.function;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Random;

/**
 * description: UserFunction <br>
 * date: 2020/12/15 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 用户相关的数据工厂工具类生成
 */
@Service
public class UserFunction {
    private static Random random = new Random();

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
    public  String getOneTel() {
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
    public  Date getOneDate() {
        int year = random.nextInt(MAX_YEAR) % (MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;
        int month = random.nextInt(MAX_MONTH) % (MAX_MONTH - MIN_MONTH + 1) + MIN_MONTH;
        int day = random.nextInt(MAX_DAY) % (MAX_DAY - MIN_DAY + 1) + MIN_DAY;
        return localDate2Date(LocalDate.of(year, month, day));
    }

    /**
     * 获取一个邮箱
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public  String getOneEmail(int lMin, int lMax) {
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
    public  String getRandomNumber(int len) {
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


    private  int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}
