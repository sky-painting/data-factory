package com.tianhua.datafactory.vo.project;

import java.util.List;
import java.util.Map;

import com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum;
import com.tianhua.datafactory.vo.BaseVO;
import com.tianhua.datafactory.vo.model.ParamModelVO;
import lombok.Data;
import lombok.ToString;

 /**
 * @Description:api模型信息类
 * @Author:
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ApiVO extends BaseVO {


     private Long id;


    /** api类型 **/
    private String apiType;

    /** api签名 **/
    private String apiSign;

    /** api返回类型 **/
    private String returnType;

    /** api请求方式 **/
    private String methodType;

     /** api返回值集合 **/
     private String returnValue;

    /** api参数可选值列表 **/
    private Map<Integer,List<Object>> paramDefaultValueList;

    /** api状态 **/
    private Integer status;

    /** api所属项目编码 **/
    private String projectCode;

    /** api参数列表 **/
    private List<ParamModelVO> paramList;


     /** api信息 **/
     private String apiUrl;


     /** api描述 **/
     private String apiDoc;


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
      * api返回包装类型名称
      */
     private String apiReturnWrapTypeDesc;

     /**
      * 上传的plantuml文件
      */
     private String file;

     /**
      * api返回类型
      */
     private String returnParamClass;

     /**
      * 请求参数列表
      */
     private String requestParamClasses;

     public String statusDesc;


 }