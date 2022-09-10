package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.Getter;

/**
* @Description:模型类型类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Getter
public enum ModelTypeEnum{

   /**
    *
    */
   API_MODEL("apiModel","api请求响应模型"),
   SERVICE_MODEL("serviceModel","服务内部模型"),
   ENTITY_MODEL("entityModel","数据库实体模型"),
   DB_MODEL("dbModel","数据库er模型"),
   ;

   /** 模型类型code **/
   private String type;
   /** 模型类型描述 **/
   private String desc;


   ModelTypeEnum(String type,String desc){
       this.type = type;
       this.desc = desc;
   }

   public static OptionsBO getOptionList(){
      OptionsBO optionsBO = new OptionsBO();
      for (ModelTypeEnum modelTypeEnum : ModelTypeEnum.values()){
         optionsBO.addOptionItem(modelTypeEnum.getDesc(), modelTypeEnum.getType());
      }
      return optionsBO;
   }

   /**
    * 数据枚举路由
    * @param enumCode
    * @return
    */
   public static boolean isModelType(String enumCode){
      return "modelType".equals(enumCode);
   }




}