package com.tianhua.datafactory.domain.bo.datafactory;

/**
 * Description
 *
 * 属性值相关的领域特定语言DSL
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class FieldDSLKeyConstant {
    /**
     * 属性值前缀
     */
    public static final String  PREFIX = "prefix";

    /**
     * 属性值后缀
     */
    public static final String  SUBFIX = "subfix";
    /**
     * 属性值依赖另外一个属性值
     */
    public static final String  RELY_FIELD = "relyField";

    /**
     * 属性值依赖特定的数据源编码
     */
    public static final String  RELY_SOURCE_CODE = "relySourceCode";

    /**
     * 如果是集合类型的属性则需要生成多少条作为随机值集合
     */
    public static final String  RELY_COUNT = "relyCount";

    /**
     * 函数变量值
     */
    public static final String  FUNC_VAR = "funcVar";


    /**
     * 如果是list类型，属性值依赖的内容
     */
    public static final String  RELY_LIST_FIELD = "relyListField";

    /**
     * 如果是Set类型，属性值依赖的内容
     */
    public static final String  RELY_SET_FIELD = "relySetField";

    /**
     * 如果属性是map kv的则需要定义key,value相关依赖
     */
    public static final String RELY_MAP_KEY_FIELD = "relyMapKeyField";


    public static final String RELY_MAP_VALUE_FIELD = "relyMapValueField";


    //public static final String  RELY_LIST_FIELD = "relyListField";

}
