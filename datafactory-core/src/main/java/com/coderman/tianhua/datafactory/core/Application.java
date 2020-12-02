package com.coderman.tianhua.datafactory.core;

import com.coderman.utils.bean.CglibConvertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


/**
* @Description:应用启动入口
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

    @Bean
    public CglibConvertService cglibConvertService(){
        return new CglibConvertService();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
