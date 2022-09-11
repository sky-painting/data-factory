package com.tianhua.datafactory.domain.bo.datafactory;

import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import lombok.Data;

import java.util.List;

/**
 * Description
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class ApiMockBO {

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * api接口签名
     */
    private String apiSign;


    /**
     * 适用于对外接口返回场景
     */
    private Boolean successData;


    /**
     * mock数据结果
     */
    private String mockResultData;

    /**
     * 与api sign一样
     */
    private String apiMethod;

    /**
     * 参数模型
     *
     */
    private List<ParamModelBO> paramModelList;

    /**
     * mock数据的数量
     */
    private Integer mockCount;
}
