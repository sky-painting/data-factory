package com.lightsnail.tianhua.datafactory.core.entity;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:数据源管理表Entity类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@Data
@ToString
public class DataSourceEntity{


   /** 主键 **/
   private Long id;

   /** 数据源名称 **/
   private String sourceName;

   /** 数据源code,唯一 **/
   private String sourceCode;

   /** 数据源类型/(nacos,api,enum) **/
   private Integer sourceType;

   /** 数据源提供来源 **/
   private String providerSrc;

   /** 访问token **/
   private String tokent;

   /** 数据源访问url **/
   private String url;

   /** 状态(0正常,1过期) **/
   private Integer status;

   /** 访问策略（0动态获取/1本地缓存） **/
   private Integer visitStrategy;

   /** 创建时间 **/
   private Date createTime;

   /** 修改时间 **/
   private Date updateTime;

   /** 创建人id **/
   private Long createUserId;

   /** 修改人id **/
   private Long updateUserId;

}