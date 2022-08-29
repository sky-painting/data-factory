package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:参数模型表DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ParamModelDO{


   /** 主键 **/
   private Long id;

   /** 项目名称 **/
   private String projectCode;

   /** 参数类名 **/
   private String paramClassName;


   /**
    * 模型参数名称
    * 模型作为api参数使用
    */
   private String paramVarName;



   /** 参数类描述 **/
   private String paramClassDesc;

   /** 所属上下文或模块编码 **/
   private String moduleCode;

   /** 模型后缀 **/
   private String modelSuffix;



   /** 创建时间 **/
   private Date dateCreate;

   /** 修改时间 **/
   private Date dateUpdate;

   /** 修改人 **/
   private Long updateUserId;

   /** 创建人 **/
   private Long createUserId;

}