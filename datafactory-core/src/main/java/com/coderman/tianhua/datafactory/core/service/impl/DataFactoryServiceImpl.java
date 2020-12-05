package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.utils.response.ResultDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: DataFactoryServiceImpl <br>
 * date: 2020/12/5 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class DataFactoryServiceImpl implements DataFactoryService {
    @Override
    public ResultDataDto generateSimple(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList) {
        return null;
    }
}
