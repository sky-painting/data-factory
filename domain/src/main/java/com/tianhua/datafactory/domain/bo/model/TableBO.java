package com.tianhua.datafactory.domain.bo.model;

import java.util.List;

import com.tianhua.datafactory.domain.bo.BaseBO;
import lombok.Data;
import lombok.ToString;

/**
* @Description:数据库table模型信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class TableBO extends BaseBO {


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
   private  List<ColumnBO> columnList;


   private String file;


   /**
    * 是否快捷构建JavaEntity模型
    */
   private Boolean buildJavaEntity;


   /** 模型后缀 **/
   private String modelSuffix;

   public boolean buildJavaEntity(){
      return buildJavaEntity != null && buildJavaEntity == true;
   }


}