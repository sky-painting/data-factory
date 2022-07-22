package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:字段模型DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ColumnModelDO{


   /** 主键 **/
   private Long id;

   /** 所属数据库 **/
   private Long tableId;

   /** 字段名称 **/
   private String columnName;

   /** 字段描述 **/
   private String columnComment;

   /** 字段长度 **/
   private Integer columnLength;

   /** 字段类型 **/
   private String columnType;

   /** 是否可为空 **/
   private Boolean notNull;

   /** 默认值 **/
   private String defaultValue;

   /** 是否是唯一键 **/
   private Boolean uniqColumn;

   /** 创建时间 **/
   private Date dateCreate;

   /** 修改时间 **/
   private Date dateUpdate;

   /** 修改人 **/
   private Long updateUserId;

   /** 创建人 **/
   private Long createUserId;

}