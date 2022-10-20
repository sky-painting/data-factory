package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Description
 * date: 2022/8/8
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dateTimeFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.DATA_TIME)
public class DateTimeFunc implements Function {
    private static SecureRandom random = new SecureRandom();
    private static final int MIN_YEAR = 1999;
    private static final int MAX_MONTH = 11;

    private static final int SECOND = 59;

    private static final int HOUR = 23;
    @Override
    public Object createOneData(String... params) {
        int year = random.nextInt(30) + MIN_YEAR;
        int month = random.nextInt(MAX_MONTH) + 1;
        int day;

        if(month == 2){
            day = random.nextInt(27) + 1;
        }else if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 12) {
            day = random.nextInt(30) + 1;
        }else {
            day = random.nextInt(29) + 1;
        }
        return localDate2Date(LocalDateTime.of(year, month, day, random.nextInt(HOUR), random.nextInt(SECOND), random.nextInt(SECOND)));

    }


    /**
     * localDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    private Date localDate2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());
        return date;
    }
}
