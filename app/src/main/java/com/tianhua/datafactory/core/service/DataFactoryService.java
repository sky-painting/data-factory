package com.tianhua.datafactory.core.service;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.DataBuildRequestBean;

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
     * @param dataFactoryRequestBean
     * @return
     */
    ResultDataDto generateData(DataBuildRequestBean dataFactoryRequestBean) throws Exception;

    /**
     * 通过数据源编码构建数据源
     * @param dataSourceCode
     * @return
     */
    String buildData(String dataSourceCode) ;

}
