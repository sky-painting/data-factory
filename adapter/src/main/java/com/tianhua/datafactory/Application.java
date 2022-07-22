/*
package com.tianhua.datafactory;

import com.coderman.utils.bean.CglibConvertService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


*/
/**
* @Description:应用启动入口
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*//*

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.tianhua.**","com.alibaba.nacos"})
@MapperScan(value = "com.tianhua.datafactory.core.mapper")
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
*/
