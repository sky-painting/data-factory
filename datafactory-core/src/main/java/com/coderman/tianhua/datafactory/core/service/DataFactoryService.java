package com.coderman.tianhua.datafactory.core.service;

import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.utils.response.ResultDataDto;

import java.util.List;

/**
 * description: DataFactoryservice <br>
 * date: 2020/12/5 23:39 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface DataFactoryService {
    /**
     * 生成简单数据列表
     * @param dataFactoryRequestFieldBeanList
     * @return
     */
    ResultDataDto generateSimple(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList);
}
