package com.coderman.tianhua.datafactory.core.bean;

import com.coderman.tianhua.datafactory.client.function.Function;
import lombok.Data;

import java.security.SecureRandom;
import java.util.Map;

/**
 * Description:
 * date: 2021/1/16
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DataSourceFieldRequestBean {

    /**
     * 当前请求的字段
     */
    private DataFactoryRequestFieldBean dataFactoryRequestFieldBean;
    /**
     * 随机数对象
     */
    private  SecureRandom random;

    //依赖的数据字段值
    private Map<String, Object> fieldValueMap;

    /**
     * 内置的生成数据的随机函数
     */
    private Function function;
}
