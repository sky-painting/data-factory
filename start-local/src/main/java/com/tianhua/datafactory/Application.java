package com.tianhua.datafactory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * Description:
 * date: 2022/3/21
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@SpringBootApplication(scanBasePackages = {"com.tianhua.datafactory"})
@MapperScan(value = "com.tianhua.datafactory.infrast.dao.mapper")

public class Application {
    // 文件上传配置类
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大2M
        factory.setMaxFileSize(DataSize.parse("10240KB")); //KB,MB
        /// 设置总上传数据总大小5M
        factory.setMaxRequestSize(DataSize.parse("102400KB"));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
