package com.coderman.tianhua.datafactory.core.bean;

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
public class DataFactoryRequestFieldBean<T> {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldTypeStr;
    /**
     * 字段数据源code
     */
    private String dataSourceCode;

    /**
     * 字段数据源code对应的值如果是JSON情况，则存在多重包装，因此需要取到对应的子JSON串的field
     * eg:data.id
     * data.list[0].id
     *
     * 当defaultValueList不为空时或者是依赖默认内置Function生成数据时可不传此值
     */
    private String dataSourceField;

    /**
     * 字段默认值列表
     */
    private List<T> defaultValueList;

    /**
     * 用于具有一对多的数据依赖关系场景，当存在这种情况时多方不必定义
     * dataSourceCode,dataSourceField 多方取值根据一方的实际值进行路由，然后随机取一个值作为最终的值
     *
     * 数据字段依赖规则
     * map.key = 字段被依赖方k-字段被依赖方value
     * map.value= 字段依赖方k-字段依赖方value（有多个字段kd依赖k的值）
     *
     * k:字段被依赖方
     * kd:字段依赖方（有多个字段kd依赖k的值）
     * map<k-v,List<kd-v>>
     *
     * 这里举例，假设k对应的value为1的时候 kd1对应的值有1,2,3 kd2对应的值有3,4,5
     * k对应的value为2的时候 kd1对应的值有6,7,8 kd2对应的值有1,2
     *
     * 这里依赖的是静态值
     */
    private Map<String,List<String>> varDependencyMap;

    /**
     * 字段值生成规则
     */
    private DataFactoryRequestFieldRuleBean dataFactoryRequestFieldRuleBean;

}
