package com.coderman.tianhua.datafactory.core.bean;

import lombok.Data;
import lombok.ToString;

/**
 * description: DataSourceBean <br>
 * date: 2020/12/5 23:47 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
@ToString
public class DataSourceBean {
    /** 主键 **/
    private Long id;

    /** 数据源内容 **/
    private String dataContentJson;

    /** 数据源名称 **/
    private String sourceName;

    /** 数据源code,唯一 **/
    private String sourceCode;

    /** 数据源类型/(nacos,api,enum) **/
    private Integer sourceType;

    /** 数据源提供来源 **/
    private String providerSrc;

    /** 访问token **/
    private String tokent;

    /** 数据源访问url **/
    private String url;

    /** 状态(0正常,1过期) **/
    private Integer status;

    /** 访问策略（0动态获取/1本地缓存） **/
    private Integer visitStrategy;


}
