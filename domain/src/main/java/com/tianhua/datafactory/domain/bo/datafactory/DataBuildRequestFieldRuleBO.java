package com.tianhua.datafactory.domain.bo.datafactory;

import lombok.Data;

import java.util.List;

import java.util.Set;

/**
 * Description
 * date: 2022/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DataBuildRequestFieldRuleBO {

    /**
     * 当前属性值的默认前缀
     */
    private String prefix;

    /**
     * 当前属性值的默认后缀
     */
    private String subfix;


    /**
     * 当前属性依赖的另外一个属性名称
     *  a.b.c.xxFieldValue = currentFieldValue
     */
    private String relyField;

    /**
     * list的默认值
     */
    private List listDefault;

    /**
     * set的默认值
     */
    private Set setDefault;


    /**
     * 当前依赖的List<BO>,属性要生成多少条
     */
    private Integer relyCount;

    /**
     * 属性值需要的数据源编码
     */
    private String relySourceCode;

    /**
     * 当前属性值依赖的脚本计算内容
     */
    private String relyScript;

    /**
     * 当前属性如果依赖具体函数,但是需要传入参数灵活控制则可以使用这个
     * 如果是依赖某个属性的值作为内置函数的参数则可以使用$fieldName
     */
    private String funcVar;


    /**
     * 如果是map结构，则标示key对应的规则
     */
    private String relyMapKeyField;
    /**
     * 如果是map结构，则标示value对应的规则
     */
    private String relyMapValueField;

    private String relySetField;

    private String relyListField;

    private String relyArrayField;




}
