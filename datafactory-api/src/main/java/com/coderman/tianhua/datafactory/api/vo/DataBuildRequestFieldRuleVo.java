package com.coderman.tianhua.datafactory.api.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Map;


/**
 * description: DataFactoryRequestFieldRuleBean <br>
 * date: 2020/12/29 23:35 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 字段生成的特殊规则
 */
@Data
@ToString
public class DataBuildRequestFieldRuleVo {
    /**
     * 值前缀--适用于str类型
     */
    private String prefixStr;

    /**
     * 值后缀--适用于str类型
     */
    private String subfixStr;

    /**
     * 动态依赖的函数类和方法
     * 通过code能找到对应的函数类，
     * 但是需要对应的函数中的参数
     */
    private String depencyFunctionMethod;

    /**
     * 动态依赖的变量名称
     *
     */
    private String dependencyFieldName;

    /**
     * 动态依赖的函数方法的参数
     */
    private String [] depencyFunctionMethodParam;


    /**
     * 远程数据源接口访问参数
     */
    private Map<String,Object> parameterMap;

    /**
     * 对于该属性对应的值生成数量，默认多个按逗号隔开
     * 如果不设置值或者小于0则默认只生成一个
     */
    private int valueCount;

    /**
     * 值存在多个的情况下的分割符，默认逗号分割
     */
    private String splitTag;



}
