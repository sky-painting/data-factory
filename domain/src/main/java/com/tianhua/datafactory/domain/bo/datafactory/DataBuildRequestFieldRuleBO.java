package com.tianhua.datafactory.domain.bo.datafactory;

import lombok.Data;

import java.util.List;
import java.util.Map;
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

    private List listDefault;

    private Set setDefault;


    /**
     * 当前依赖的List<BO>,属性要生成多少条
     */
    private Integer relyCount;

    /**
     * 当前依赖的List<BO>,BO的属性依赖哪些数据源
     * 如果没有则使用默认的数据源
     */
    private Map<String,String> relySourceCodeMap;

    /**
     * 当前属性值依赖的脚本计算内容
     */
    private String relyScript;

    /**
     * 当前属性如果依赖具体函数,但是需要传入参数灵活控制则可以使用这个
     * 如果是依赖某个属性的值作为内置函数的参数则可以使用$fieldName
     */
    private String funcVar;


    private String relyMapKeyField;

    private String relyMapValueField;

    private String relySetField;

    private String relyListField;

    private String relyArrayField;




}
