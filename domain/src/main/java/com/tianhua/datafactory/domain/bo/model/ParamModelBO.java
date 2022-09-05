package com.tianhua.datafactory.domain.bo.model;

import java.util.ArrayList;
import java.util.List;

import com.tianhua.datafactory.domain.bo.BaseBO;
import lombok.Data;
import lombok.ToString;

/**
* @Description:参数模型信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ParamModelBO extends BaseBO {


   /** 模型类名称 **/
   private  String paramClassName;

   /**
    * 模型参数名称
    * 模型作为api参数使用
    */
   private String paramVarName;


   /** 所属项目名称 **/
   private  String projectCode;

   /** 属性列表 **/
   private  List<FieldBO> fieldBeanList;

   /** 主键 **/
   private  Long id;

   private  String moduleCode;

   /** 参数类描述 **/
   private  String paramClassDesc;

   /** 参数属性集合json **/
   private  String paramFieldJson;

   /** 模型后缀 **/
   private String modelSuffix;

   /** 是否是泛型参数 **/
   private boolean isGeneralType;

   /**
    * 状态
    * @See com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum
    */
   private Integer status;

   private String statusDesc;

   /** 参数映射模型 **/
   private  List<ModelMappingBO> modelMappingBOList;
   public ParamModelBO(){}

   public ParamModelBO(String paramClassName){
      this.paramClassName = paramClassName;
   }

   public ParamModelBO(String paramClassName, String paramVarName){
      this.paramClassName = paramClassName;
      this.paramVarName = paramVarName;
   }


   public static  ParamModelBO getInstance(String paramClassName){
      return new ParamModelBO(paramClassName);
   }


   public static  ParamModelBO getInstance(String paramClassName, String paramVarName){
      return new ParamModelBO(paramClassName, paramVarName);
   }

   /**
    *
    * @Description: 是否是模型参
    * @return boolean
    */
   public boolean isModelType(){

       return false;
   }


   public void addField(FieldBO fieldBO){
      if(this.fieldBeanList == null){
         fieldBeanList = new ArrayList<>();
      }
      fieldBeanList.add(fieldBO);
   }


}