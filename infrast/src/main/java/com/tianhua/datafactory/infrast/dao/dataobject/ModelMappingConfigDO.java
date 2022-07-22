package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:模型映射表DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ModelMappingConfigDO{

   private  Long id;

   /**
    * 映射模型类名 or表名
    */
   private String mappingClassFirst;

   /**
    * 映射模型属性名
    */
   private String mappingFieldFirst;


   /**
    * 映射模型类名 or表名
    */
   private String mappingClassSecond;

   /**
    * 映射模型属性名
    */
   private String mappingFieldSecond;


   /** 映射类型 **/
   private String mappingType;

   /** 项目编码 **/
   private String projectCode;

}