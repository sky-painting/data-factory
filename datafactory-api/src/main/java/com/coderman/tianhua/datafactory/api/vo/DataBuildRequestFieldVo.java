package com.coderman.tianhua.datafactory.api.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * description: DataFactoryRequestFieldVo <br>
 * date: 2020/12/5 23:31 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
@ToString
public class DataBuildRequestFieldVo<T> {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldTypeStr;
    /**
     * 字段值数据源code
     */
    private String dataSourceCode;


    /**
     * 数据值解析寻址json  path
     * eg:data.id
     * data.list[0].id
     */
    private String dataSourceField;
    /**
     * 字段默认值列表
     */
    private List<T> defaultValueList;

    /**
     * 数据字段依赖规则
     * map.key = 字段被依赖方k-字段被依赖方value
     * map.value= 字段依赖方k-字段依赖方value（有多个字段kd依赖k的值）
     */
    private Map<String,List<String>> varDependencyMap;

    /**
     * 字段值生成规则
     */
    private DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo;


    /**
     * 数据库字段名
     */
    private String columnName;


}
