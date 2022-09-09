package com.tianhua.datafactory.vo.project;

import com.tianhua.datafactory.vo.BaseVO;
import com.tianhua.datafactory.vo.model.ParamModelVO;
import lombok.Data;

import java.util.List;

/**
 * Description
 * api数据mock
 * date: 2022/9/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class ApiMockVO extends BaseVO {

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
    private List<ParamModelVO> paramModelVOList;

}
