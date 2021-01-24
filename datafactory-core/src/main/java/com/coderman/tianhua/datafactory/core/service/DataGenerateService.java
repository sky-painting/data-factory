package com.coderman.tianhua.datafactory.core.service;

import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;

/**
 * description: DataRandomAccessService 随机数据访问服务
 * date: 2021/1/17 20:20 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface DataGenerateService {


    /**
     * 生成随机数据
     * @param dataSourceFieldRequestBean
     * @return
     */
    Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean);
}
