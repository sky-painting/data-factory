package com.tianhua.datafactory.infrast.dao.dataobject;

import java.util.Date;

import com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum;
import lombok.Data;
import lombok.ToString;

/**
* @Description:API数据表DO类
* @Author:shenshuai
* @CreateTime:2022-05-27 16:05:26
* @version v1.0
*/
@Data
@ToString
public class ApiModelDO{


   /** 主键 **/
   private Long id;

   /** 项目名称 **/
   private String projectCode;

   /** api信息 **/
   private String apiUrl;

   /** api类型 **/
   private String apiType;

   /** api返回值集合 **/
   private String returnValue;

   /** api签名信息 **/
   private String apiSign;

   /** api描述 **/
   private String apiDoc;

   /** 请求方式 **/
   private String methodType;

   /** 请求参数元信息 **/
   private String requestParam;


   /**
    * 进行接口mock调用的时候接口返回的条数
    * 1条或者多条
    * 模拟后端数据返回
    */
   private Integer mockCount;

   /**
    * api返回包装类型
    * @see ReturnWrapClassEnum
    */
   private Integer apiReturnWrapType;



   /**
    * 响应参数元信息
    */
   private String returnParam;

   /** api状态 **/
   private Integer status;

   /** 创建时间 **/
   private Date dateCreate;

   /** 修改时间 **/
   private Date dateUpdate;

   /** 修改人 **/
   private Long updateUserId;

   /** 创建人 **/
   private Long createUserId;


}