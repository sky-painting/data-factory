package com.tianhua.datafactory;

import com.tianhua.datafactory.client.function.impl.DateTimeFunc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Description
 * date: 2022/9/18
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class DateTimeTest {

    @Resource
    private DateTimeFunc dateTimeFunc;


    @Test
    public void testDateTime(){
        Date date = (Date) dateTimeFunc.createOneData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义新的日期格式

        String dateString = formatter.format(date);
        log.info("dateString = {}",dateString);
    }
}
