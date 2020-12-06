package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.utils.response.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class DataFactoryServiceImpl implements DataFactoryService {

    private ThreadLocal<Random> randomThreadLocal = new ThreadLocal<>();

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public ResultDataDto generateSimple(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList) {
        randomThreadLocal.set(new Random());
        for (DataFactoryRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList){
        }
        randomThreadLocal.remove();
        return null;
    }

    /**
     * 获取数据字段值
     * @param dataFactoryRequestFieldBean
     * @return
     * @throws Exception
     */
    private Object getRandomValue(DataFactoryRequestFieldBean dataFactoryRequestFieldBean) throws Exception {
        //从默认值中获取数据
        if(StringUtils.isEmpty(dataFactoryRequestFieldBean.getDataSourceCode())){
            if(CollectionUtils.isEmpty(dataFactoryRequestFieldBean.getDefaultValueList())){
                return null;
            }
            int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
            return dataFactoryRequestFieldBean.getDefaultValueList().get(randomThreadLocal.get().nextInt(size));
        }
        //从数据工厂-数据源获取数据
        ResultDataDto<String> resultDataDto = dataSourceService.getDataSourceDetail(dataFactoryRequestFieldBean.getDataSourceCode());
        if(!resultDataDto.isSuccess()){
            log.error("从数据工厂-数据源获取数据 = {}", JSON.toJSONString(resultDataDto));
            return null;
        }

        String jsonValue = resultDataDto.getData();

        JSONArray jsonArray = JSONObject.parseArray(jsonValue);

        String jsonPath = dataFactoryRequestFieldBean.getDataSourceJsonPath();






        return null;
    }

}
