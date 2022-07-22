package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

/**
* @Description:项目配置DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ProjectConfigDO{


   /** 主键 **/
   private Long id;

   /** 项目应用名 **/
   private String projectCode;

   /** 应用描述 **/
   private String projectDesc;

   /** 业务领域编码 **/
   private String domainCode;

   /** 业务领域描述 **/
   private String domainDesc;

   /** 所属业务线 **/
   private String busLine;

   /** 所属部门 **/
   private String deptName;

   /** 创建时间 **/
   private Date dateCreate;

   /** 修改时间 **/
   private Date dateUpdate;

   /** 修改人 **/
   private Long updateUserId;

   /** 创建人 **/
   private Long createUserId;

}