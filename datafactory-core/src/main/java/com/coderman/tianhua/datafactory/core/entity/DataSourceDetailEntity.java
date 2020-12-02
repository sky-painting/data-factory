package com.coderman.tianhua.datafactory.core.entity;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:数据源详情表Entity类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@Data
@ToString
public class DataSourceDetailEntity{


   /** 主键 **/
   private Long id;

   /** 所属数据源id **/
   private Long dataSourceId;

   /** 数据源内容 **/
   private String dataContentJson;

}