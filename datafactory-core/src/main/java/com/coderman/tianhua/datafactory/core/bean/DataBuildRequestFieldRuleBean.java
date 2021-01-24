package com.coderman.tianhua.datafactory.core.bean;

import lombok.Data;
import lombok.ToString;


/**
 * description: DataFactoryRequestFieldRuleBean <br>
 * date: 2020/12/29 23:35 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 字段生成的特殊规则
 */
@Data
@ToString
public class DataBuildRequestFieldRuleBean {
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


}
