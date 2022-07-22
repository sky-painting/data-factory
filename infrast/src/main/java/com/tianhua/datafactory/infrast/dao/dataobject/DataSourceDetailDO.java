package com.tianhua.datafactory.infrast.dao.dataobject;

import lombok.Data;
import lombok.ToString;

/**
* @Description:数据源详情表DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class DataSourceDetailDO{


   /** 主键 **/
   private Long id;

   /** 所属数据源id **/
   private Long dataSourceId;

   /** 数据源内容 **/
   private String dataContentJson;

}