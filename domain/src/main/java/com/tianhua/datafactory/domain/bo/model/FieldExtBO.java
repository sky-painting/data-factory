package com.tianhua.datafactory.domain.bo.model;

import lombok.Data;

import java.util.List;

/**
 * Description:属性扩展字段
 * date: 2022/6/13
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class FieldExtBO {

    /**
     * 属性关联的数据源
     */
    private String dataSourceCode;

    /**
     * 是否可以代表业务唯一性
     */
    private Boolean bizUnique;

    /**
     * 是否可以作为领域实体标示
     */
    private Boolean bizIdentifier;

    /**
     * 是否是复杂数据类型(list,map,set,objrefer)
     */
    private Boolean complexStruct;


    /**
     * 本身是否存在特定规则校验(邮箱格式,域名格式，身份证格式，银行卡格式,姓名中文英文等等)
     */
    private Boolean checkRule;


    /**
     * 是否对应数据库字段
     */
    private Boolean mappingDBColumn;


    /**
     * 默认值列表(不超过3个，建议常量或者是否之类的，否则使用固定数据源)
     */
    private List<String> defaultValueList;


    /**
     * 值前缀--适用于str类型
     */
    private String prefixStr;

    /**
     * 值后缀--适用于str类型
     */
    private String subfixStr;


    /**
     * 构建的属性模型DSL描述
     */
    private String buildRuleDSL;

}
