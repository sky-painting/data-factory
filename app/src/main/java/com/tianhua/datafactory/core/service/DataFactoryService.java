package com.tianhua.datafactory.core.service;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.DataBuildRequestBean;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;

import java.util.List;
import java.util.Map;

/**
 * description: DataFactoryservice <br>
 * date: 2020/12/5 23:39 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface DataFactoryService {


   // ResultDataDto generateSimplex(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList) throws Exception;

    /**
     * 生成简单数据列表
     * @param dataBuildRequestBO
     * @return
     */
    ResultDataDto<List<Map<String, Object>>> generateData(DataBuildRequestBO dataBuildRequestBO) throws Exception;

    /**
     * 通过数据源编码获取单个数据源对应的随机数
     * 主要用于测试
     * @param dataBuildRequestFieldBO
     * @return
     */
    String buildData(DataBuildRequestFieldBO dataBuildRequestFieldBO) ;

}
