package com.tianhua.datafactory.domain.bo;

import com.tianhua.datafactory.domain.bo.project.ApiBO;
import lombok.Data;

import java.util.Map;

/**
 * Description
 *
 * java泛型模型
 * date: 2022/8/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class GenericTypeBO {

    /**
     * 泛型外部包装类型
     */
    private String wrapType;

    /**
     * 泛型被包裹的类型
     */
    private String realType;

    /**
     * map-key的类型
     */
    private String realKeyType;
    /**
     * map-value的类型
     */
    private String realValueType;

    /**
     * 泛型子类型
     */
    private Map<String, GenericTypeBO> subGenericTypeMap;

    /**
     * 对应的api模型
     */
    private ApiBO apiBO;

}
