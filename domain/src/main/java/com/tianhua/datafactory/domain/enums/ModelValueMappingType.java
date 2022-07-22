package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.Getter;

/**
* @Description:模型映射类型类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Getter
public enum ModelValueMappingType{

   /**
    *
    */
   JSON_MAPPING("jsonMapping","json结构映射"),
   ENUM_MAPPING("enumMapping","enum映射"),
   KEY_MAPPING("keyMapping","map数据结构的key映射"),
   VALUE_MAPPING("valueMapping","名称一样，类型不一样"),
   DIRECT_MAPPING("directMapping","直接映射,类型与名称相同"),
   ;

   /** 模型类型code **/
   private String type;
   /** 模型类型描述 **/
   private String desc;


   ModelValueMappingType(String type,String desc){
       this.type = type;
       this.desc = desc;
   }



   public static OptionsBO getOptionList(){
      OptionsBO optionsBO = new OptionsBO();
      for (ModelValueMappingType modelValueMappingType : ModelValueMappingType.values()){
         optionsBO.addOptionItem(modelValueMappingType.getDesc(), modelValueMappingType.getType());
      }
      return optionsBO;
   }

   public static boolean isModelValueMapping(String enumCode){
      return "modelValueMapping".equals(enumCode);
   }



}