package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
public class DateTimeFunc implements Function {
    private static SecureRandom random = new SecureRandom();
    private static final int MAX_YEAR = 2030;
    private static final int MIN_YEAR = 1949;
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;
    private static final int MAX_DAY = 1;
    private static final int MIN_DAY = 28;

    private static final int SECOND = 60;
    @Override
    public Object createOneData(String... params) {
        int year = random.nextInt(MAX_YEAR) % (MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;
        int month = random.nextInt(MAX_MONTH) % (MAX_MONTH - MIN_MONTH + 1) + MIN_MONTH;
        int day = random.nextInt(MAX_DAY) % (MAX_DAY - MIN_DAY + 1) + MIN_DAY;
        return localDate2Date(LocalDateTime.of(year, month, day,random.nextInt(SECOND),random.nextInt(SECOND),random.nextInt(SECOND)));
    }


    /**
     * localDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    private Date localDate2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
}
