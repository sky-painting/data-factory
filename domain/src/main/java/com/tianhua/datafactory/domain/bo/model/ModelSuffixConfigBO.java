package com.tianhua.datafactory.domain.bo.model;


import lombok.Data;
import lombok.ToString;

/**
* @Description:模型后缀信息类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ModelSuffixConfigBO {

   private Long id;



   /** 模型后缀 **/
   private  String suffix;

   /** 模型后缀描述 **/
   private  String desc;

   /** 模型类型 **/
   private  String modelType;


}