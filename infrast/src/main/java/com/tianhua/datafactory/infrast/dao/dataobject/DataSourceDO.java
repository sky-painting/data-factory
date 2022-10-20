package com.tianhua.datafactory.infrast.dao.dataobject;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
* @Description:数据源管理表Entity类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@Data
@ToString
public class DataSourceDO {


   /** 主键 **/
   private Long id;

   /** 数据源名称 **/
   private String sourceName;

   /** 数据源code,唯一 **/
   private String sourceCode;

   /** 数据源类型/(nacos,api,enum) **/
   private Integer sourceType;

   /** 数据源提供来源服务名 **/
   private String providerService;

   /**
    * 服务提供者域名
    */
   private String providerDomainUrl;

   /**
    * 注册中心类型
    */
   private Integer registServer;



   /** 数据源访问url **/
   private String url;

   /** 状态(0正常,1过期) **/
   private Integer status;

   /** 访问策略 **/
   private Integer visitStrategy;


   /**
    * 数据源格式
    */
   private String structType;









   /** 创建时间 **/
   private Date createTime;

   /** 修改时间 **/
   private Date updateTime;

   /** 创建人id **/
   private Long createUserId;

   /** 修改人id **/
   private Long updateUserId;

}