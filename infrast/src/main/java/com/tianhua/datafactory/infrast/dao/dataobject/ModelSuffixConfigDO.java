package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:后缀配置表DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ModelSuffixConfigDO{


   /** 主键 **/
   private Long id;

   /** 模型后缀 **/
   private String suffix;

   /** 模型后缀描述 **/
   private String desc;

   /** 模型类型 **/
   private String modelType;

}