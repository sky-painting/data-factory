package com.tianhua.datafactory.vo.model;


import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description:数据库table模型信息类
 * @Author:
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class TableVO {

    private Long id;

    /** 数据库表名 **/
    private  String tableName;

    /** 数据库表描述 **/
    private  String tableComment;

    /** 数据库名称 **/
    private  String dbName;

    /** 项目编码 **/
    private  String projectCode;

    /** 数据库字段列表 **/
   private List<ColumnVO> columnList;

   private String file;


    /**
     * 是否快捷构建JavaEntity模型
     */
    private Boolean buildJavaEntity;

    /** 模型后缀 **/
    private String modelSuffix;

    /**
     * 状态
     * @See com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum
     */
    private Integer status;
    public String statusDesc;

}