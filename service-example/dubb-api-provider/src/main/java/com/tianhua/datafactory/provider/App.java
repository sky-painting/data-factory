package com.tianhua.datafactory.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.tianhua.datafactory.provider"})
@EnableDubbo
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
