package com.tianhua.datafactory.infrast.dao.dataobject;

import lombok.Data;
import lombok.ToString;

/**
* @Description:属性模型DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class FieldModelDO{


   /** 主键 **/
   private Long id;

   /** 属性名称 **/
   private String fieldName;

   /** 属性描述 **/
   private String fieldDesc;

   /** 属性中文注释 **/
   private String fieldDoc;


   /** 所属类模型名称 **/
   private String paramClassName;

   /** 项目编码 **/
   private String projectCode;
   /**
    * 属性类型
    */
   private String fieldType;


   /**
    * 属性扩展字段集合
    */
   private String fieldExtJsonStr;


   /**
    * 状态
    */
   private Integer status;
}