package com.tianhua.datafactory.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Description
 * 解决jacksonxml无法进行json转换的问题，这里使用fastjson的解决方案
 * date: 2022/9/6
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Configuration
public class SpringWebMvcConfigurer implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(SpringWebMvcConfigurer.class);

    //使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

          /*
          先把JackSon的消息转换器删除.
          备注: (1)源码分析可知，返回json的过程为:
                     Controller调用结束后返回一个数据对象，for循环遍历conventers，找到支持application/json的HttpMessageConverter，然后将返回的数据序列化成json。
                     具体参考org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor的writeWithMessageConverters方法
                (2)由于是list结构，我们添加的fastjson在最后。因此必须要将jackson的转换器删除，不然会先匹配上jackson，导致没使用fastjson
         */
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero,//Number null -> 0
                SerializerFeature.WriteDateUseDateFormat);//日期格式化
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }
}
