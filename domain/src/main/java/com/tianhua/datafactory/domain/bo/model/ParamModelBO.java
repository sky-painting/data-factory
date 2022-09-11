package com.tianhua.datafactory.domain.bo.model;

import java.util.ArrayList;
import java.util.List;

import com.tianhua.datafactory.domain.bo.BaseBO;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
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
    * 接口 出入参为string,long,integer之类的参数类型,从前端绑定
    *
    */
   private String dataSourceCode;



   /**
    * 接口 出入参为string,long,integer之类的参数类型, 构建的属性模型DSL描述
    */
   private String buildRuleDSL;

   /**
    * 出入参为string,long,integer之类的参数类型, 默认值列表(不超过3个，建议常量或者是否之类的，否则使用固定数据源)
    */
   private String defaultValueList;



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


   public void addField(FieldBO fieldBO){
      if(this.fieldBeanList == null){
         fieldBeanList = new ArrayList<>();
      }
      fieldBeanList.add(fieldBO);
   }


}