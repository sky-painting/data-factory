package com.tianhua.datafactory.vo.datasource;

import lombok.Data;

/**
 * Description:数据源响应参数配置
 * date: 2022/6/2
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DataSourceRespConfigVO {
    /**
     *
     */
    private String fieldKey;

    /**
     * 请求路径如a.b.c
     */
    private String referPath;

    /**
     * 数据描述
     */
    private String fieldDesc;

    /**
     * 数据类型
     */
    private String fieldType;
}
