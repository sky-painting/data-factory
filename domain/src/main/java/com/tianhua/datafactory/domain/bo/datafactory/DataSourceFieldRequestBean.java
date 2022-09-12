package com.tianhua.datafactory.domain.bo.datafactory;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import lombok.Data;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2021/1/16
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DataSourceFieldRequestBean {

    /**
     * 当前请求的字段
     */
    private DataBuildRequestFieldBO dataBuildRequestFieldBO;
    /**
     * 随机数对象
     */
    private  SecureRandom random;

    //当前请求数据源code 列表对应的value的集合
    private Map<String, Object> fieldValueMap;

    /**
     * 用于具有一对多的数据依赖关系场景，当存在这种情况时多方不必定义
     * dataSourceCode,dataSourceField 多方取值根据一方的实际值进行路由，然后随机取一个值作为最终的值
     *
     * 数据字段依赖规则
     * map.key = 字段被依赖方kcode-字段被依赖方value
     * map.value= 字段依赖方kcode-字段依赖方value（有多个字段kd依赖k的值）
     *
     * k:字段被依赖方
     * kd:字段依赖方（有多个字段kd依赖k的值）
     * map<kcode-v,List<kcode-v>>
     *
     * 这里举例，假设kcode对应的v为1的时候 kcode1对应的值有1,2,3 kcode2对应的值有3,4,5
     * kcode对应的value为2的时候 kd1对应的值有6,7,8 kd2对应的值有1,2
     *
     * 这里依赖的是静态值
     */
    private Map<String, List<String>> varDependencyMap;


    /**
     * 当前生成记录的索引
     */
    private Integer currentIndex;
}
