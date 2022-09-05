package com.tianhua.datafactory.vo.model;

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
public class FieldVO {

   private Long id;

   /** 属性名称 **/
   private   String fieldName;

   /** 属性描述 **/
   private  String fieldDesc;

   /** 属性中文注释 **/
   private  String fieldDoc;

   /** 关联数据源编码 **/
   private String dataSourceCode;

   /** 参数类名称 **/
   private  String paramClassName;

   /**
    * 属性类型
    */
   private String fieldType;

   /**
    * 是否可以代表业务唯一性
    */
   private Boolean bizUnique;

   /**
    * 是否可以作为领域实体标示
    */
   private Boolean bizIdentifier;

   /**
    * 是否是复杂数据类型(list,map,set,objrefer)
    */
   private Boolean complexStruct;


   /**
    * 本身是否存在特定规则校验(邮箱格式,域名格式，身份证格式，银行卡格式,姓名中文英文等等)
    */
   private Boolean checkRule;


   /**
    * 是否对应数据库字段
    */
   private Boolean mappingDBColumn;


   /**
    * 默认值列表(不超过3个，建议常量或者是否之类的，否则使用固定数据源)
    */
   private String defaultValueList;


   /**
    * 构建的属性模型DSL描述
    */
   private String buildRuleDSL;


   public String statusDesc;


   /**
    * 状态
    * @See com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum
    */
   private Integer status;
}