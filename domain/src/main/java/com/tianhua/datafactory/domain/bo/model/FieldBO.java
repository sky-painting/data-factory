package com.tianhua.datafactory.domain.bo.model;

import com.tianhua.datafactory.domain.enums.VisibilityEnum;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
* @Description:属性信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class FieldBO {

   private Long id;

   /** 属性名称 **/
   private   String fieldName;

   /** 属性描述 **/
   private  String fieldDesc;

   /** 属性中文注释
    *
    * **/
   private  String fieldDoc;


   /** 参数类名称 **/
   private  String paramClassName;


   /** 项目编码 **/
   private String projectCode;

   /**
    * 属性类型(java)
    */
   private String fieldType;


   private boolean extendFieldTag = false;


   /**
    * 扩展字段
    */
   private FieldExtBO fieldExtBO;
   /**
    * 构建属性注释
    * @param desc
    */
   public void buildDesc(String desc){
      if(desc.startsWith(VisibilityEnum.PUBLIC.getTag())
              || desc.startsWith(VisibilityEnum.PRIVATE.getTag())
              || desc.startsWith(VisibilityEnum.PROTECT.getTag())){
         String newDesc = desc.substring(1);
         this.setFieldDesc(newDesc);
      }else {
         this.setFieldDesc(desc);
      }
   }

   public void buildFieldDetail(){
      if(this.getFieldName().trim().contains(" ")){
         String [] fieldArr = this.getFieldName().trim().split(" ");
         this.setFieldType(fieldArr[0]);
         this.setFieldName(fieldArr[fieldArr.length - 1]);
      }
   }

}