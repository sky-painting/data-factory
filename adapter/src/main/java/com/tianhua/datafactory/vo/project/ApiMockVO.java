package com.tianhua.datafactory.vo.project;

import com.tianhua.datafactory.vo.BaseVO;
import lombok.Data;

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
}
