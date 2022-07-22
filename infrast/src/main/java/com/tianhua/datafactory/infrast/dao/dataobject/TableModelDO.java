package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:表模型DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class TableModelDO{


   /** 主键 **/
   private Long id;

   /** 数据库表名 **/
   private String tableName;

   /** 数据库表描述 **/
   private String tableComment;

   /** 数据库名称 **/
   private String dbName;

   /** 项目编码 **/
   private String projectCode;

   /** 创建时间 **/
   private Date dateCreate;

   /** 修改时间 **/
   private Date dateUpdate;

   /** 修改人 **/
   private Long updateUserId;

   /** 创建人 **/
   private Long createUserId;

}