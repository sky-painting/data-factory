package com.tianhua.datafactory.domain.bo.datafactory;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.domain.bo.DataBuildRequestFieldRuleBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
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
public class DataBuildRequestFieldBO<T> {
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */

    private String fieldType;

    /**
     * 字段数据源code
     */
    private String dataSourceCode;

    /**
     * 字段数据源类型
     */
    private Integer dataSourceType;


    /**
     * 字段默认值列表
     */
    private List<T> defaultValueList;


    /**
     * 是否可以代表业务唯一性
     */
    private Boolean bizUnique;

    /**
     * 是否可以作为领域实体标示
     */
    private Boolean bizIdentifier;


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
     * 内置的生成数据的随机函数
     */
    private Function function;

    /**
     * 数据源
     */
    private DataSourceBO dataSourceBO;

    /**
     * 构建的属性模型DSL描述
     */
    private String buildRuleDSL;


    /**
     * 属性值生成规则
     */
    private DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO;


    /**
     * 属性是模型类型如：
     * a引用的b属性是xxBO,xxDTO,或者List<xxBO>
     */
    private List<DataBuildRequestFieldBO> referFieldList;


    /**
     * 源属性名称
     */
    private String originFieldName;

    /**
     * 如果集合类的业务模型属性如List<XxxBO>
     * 或者Map<String, XxxBO>
     * 则realFieldType为XxxBO
     *
     */
    private String realFieldType;


    /**
     * 如果是api参数则需要属性与api参数模型绑定
     */
    private ParamModelBO paramModelBO;

    /**
     * 获取属性的泛型类型
     * @return
     */
    public String getGenerics(){

        if(fieldType.contains("List<")){
            String genericType = fieldType.replaceFirst("List<","");
            if(genericType.contains(">>")){
                return genericType.replace(">>",">");
            }
            return genericType.replace(">","");
        }

        if(fieldType.contains("Set<")){
            String genericType = fieldType.replaceFirst("Set<","");
            if(genericType.contains(">>")){
                return genericType.replace(">>",">");
            }
            return genericType.replace(">","");
        }
        return null;

    }


}
