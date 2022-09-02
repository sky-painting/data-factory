package com.tianhua.datafactory.domain.bo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class HttpApiRequestBO {
    private String url;
    /**
     * get post?
     */
    private String methodType;

    private String serviceName;

    /**
     * 参数列表
     */
    private Map<String, Object> params;


    private Map<String, Object> HeadParams;

    /**
     * 返回类型
     *
     * list
     * set
     * ResultDto
     */
    private String returnType;

    /**
     * 接口返回的参数模型中，有哪些参数可以作为数据源
     */
    private List<String> paramFieldList;

    /**
     * 数据提取规则
     */
    private Map<String, String> fieldExtractRuleMap;

    public HttpApiRequestBO(){}

    public HttpApiRequestBO(String url){
        this.url = url;
        this.params = new HashMap<>();
    }

    public HttpApiRequestBO(String url,Map<String,Object> paramMap){
        this.url = url;
        this.params = paramMap;
    }


}
