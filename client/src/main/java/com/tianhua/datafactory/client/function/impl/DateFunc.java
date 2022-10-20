package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Description
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 *
 * @since JDK 1.8
 */
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.DATE)
@Service
public class DateFunc implements Function<Date> {
    private static SecureRandom random = new SecureRandom();
    private static final int MAX_YEAR = 2030;
    private static final int MIN_YEAR = 1970;
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;
    private static final int MAX_DAY = 1;
    private static final int MIN_DAY = 28;

    @Override
    public Date createOneData( String... params) {
        int year = random.nextInt(MAX_YEAR) % (MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;
        int month = random.nextInt(MAX_MONTH) % (MAX_MONTH - MIN_MONTH + 1) + MIN_MONTH;
        int day = random.nextInt(MAX_DAY) % (MAX_DAY - MIN_DAY + 1) + MIN_DAY;
        return localDate2Date(LocalDate.of(year, month, day));
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
}
