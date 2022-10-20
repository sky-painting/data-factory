package com.tianhua.datafactory.vo.datasource;

import lombok.Data;

/**
 * Description:
 * date: 2022/6/2
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DataSourceReqConfigVO {
    private Long id;

    /**
     * 参数key
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数描述
     */
    private String paramDesc;

    /**
     * 是否是可选参数
     */
    private boolean required;

    /**
     * 是否是请求接口参数
     */
    private boolean interfaceParam;
}
