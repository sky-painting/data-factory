package com.coderman.tianhua.datafactory.core.service;

import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestBean;
import com.coderman.utils.response.ResultDataDto;

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
    ResultDataDto generateSimple(DataBuildRequestBean dataFactoryRequestBean) throws Exception;


}
