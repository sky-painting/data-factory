package com.tianhua.datafactory.domain.bo.model;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
* @Description:模型映射信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ModelMappingBO {

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
   private  String mappingType;

   /** 项目编码 **/
   private  String projectCode;

   /**
    * 关联的映射模型关系
    */
   List<ModelMappingBO> referMappingList;


   public List<String> buildMappingKey(){
      List<String> keyList = new ArrayList<>();
      String key1 = mappingClassFirst+"."+mappingFieldFirst+":"+mappingClassSecond+"."+mappingFieldSecond;
      keyList.add(key1);
      String key2 = mappingClassSecond+"."+mappingFieldSecond+":"+mappingClassFirst+"."+mappingFieldFirst;
      keyList.add(key2);

      return keyList;
   }

}