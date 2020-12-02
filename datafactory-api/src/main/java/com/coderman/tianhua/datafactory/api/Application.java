package com.coderman.tianhua.datafactory.api;

import com.coderman.utils.bean.CglibConvertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


/**
* @Description:应用启动入口
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@ComponentScan({"com.lightsnail.**","com.alibaba.nacos","com.coderman.**"})
public class Application {

    @Bean
    public CglibConvertService cglibConvertService(){
        return new CglibConvertService();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
