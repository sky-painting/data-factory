package com.coderman.tianhua.datafactory.core.bean;

import lombok.Data;

/**
 * description: DataSourceQueryDTO <br>
 * date: 2021/1/27 23:45 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
public class DataSourceQueryDTO {
    /**
     * 数据源code
     */
    private String dataSourceCode;
    /**
     * 数据源名称
     */
    private String dataSourceName;

    /** 数据源类型/(nacos,api,enum) **/
    private Integer sourceType;

    /** 状态(0正常,1过期) **/
    private Integer status;
}
