package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.utils.response.ResultDataDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * description: DataFactoryServiceImpl <br>
 * date: 2020/12/5 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class DataFactoryServiceImpl implements DataFactoryService {

    private ThreadLocal<Random> randomThreadLocal = new ThreadLocal<>();

    @Override
    public ResultDataDto generateSimple(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList) {
        randomThreadLocal.set(new Random());
        for (DataFactoryRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList){
        }
        randomThreadLocal.remove();
        return null;
    }

    private Object getRandomValue(DataFactoryRequestFieldBean dataFactoryRequestFieldBean){
        //从默认值中获取数据
        if(StringUtils.isEmpty(dataFactoryRequestFieldBean.getDataSourceCode())){
            if(CollectionUtils.isEmpty(dataFactoryRequestFieldBean.getDefaultValueList())){
                return null;
            }
            int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
            return dataFactoryRequestFieldBean.getDefaultValueList().get(randomThreadLocal.get().nextInt(size));
        }



        return null;
    }

}
