package com.tianhua.datafactory.vo.model;


import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description:参数模型信息类
 * @Author:
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ParamModelVO {

    /** 模型类名称 **/
    private  String paramClassName;


    /** 参数类描述 **/
    private  String classDesc;

    /** 所属项目名称 **/
    private  String projectCode;

    /** 主键 **/
    private  Long id;

    /** 所属上下文或模块编码 **/
    private  String moduleCode;

    /** 参数类描述 **/
    private  String paramClassDesc;

    /** 参数属性集合json **/
    private  String paramFieldJson;

    /** 是否是泛型参数 **/
    private boolean isGeneralType;

    /** 属性列表 **/
    private List<FieldVO> fieldBeanList;


    /** 模型后缀 **/
    private String modelSuffix;

    /** 参数映射模型 **/
    private  List<ModelMappingVO> modelMappingBOList;


    private String file;


}