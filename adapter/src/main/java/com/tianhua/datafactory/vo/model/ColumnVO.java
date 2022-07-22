package com.tianhua.datafactory.vo.model;


import lombok.Data;
import lombok.ToString;

 /**
 * @Description:数据库column模型信息类
 * @Author:
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ColumnVO {


    /** 字段名称 **/
    private  String columnName;

    /** 字段描述 **/
    private  String columnComment;

    /** 字段长度 **/
    private  Integer columnLength;

    /** 字段是否为空 **/
    private  boolean  notNull;

    /** 字段默认值 **/
    private  String defaultValue;

    /** 数据库编码 **/
    private  String tableName;

    /** 是否唯一键 **/
    private boolean uniqColumn;


     /** 字段类型 **/
     private String columnType;

}