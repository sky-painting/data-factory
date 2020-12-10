package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.utils.response.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     * 获取数据字段值
     * @param dataFactoryRequestFieldBean
     * @return
     * @throws Exception
     */
    private Object getRandomValue(DataFactoryRequestFieldBean dataFactoryRequestFieldBean,Random random) throws Exception {
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

        //通过json字段解析，这里提供的数据源必须是数组形式
        JSONArray jsonArray = JSONObject.parseArray(jsonValue);

        String dataSourceField = dataFactoryRequestFieldBean.getDataSourceField();
        //数组循环解析
        if(dataSourceField.contains("\\.")){

        }else {
            int index = random.nextInt(jsonArray.size());
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            //
            String fieldType = dataFactoryRequestFieldBean.getFieldTypeStr();
            switch (fieldType){
                case "short":
                    return jsonObject.getShortValue(dataSourceField);
                case "Short":
                    return jsonObject.getShort(dataSourceField);
                case "int":
                    return jsonObject.getIntValue(dataSourceField);
                case "Integer":
                    return jsonObject.getInteger(dataSourceField);
                case "long":
                    return jsonObject.getLongValue(dataSourceField);
                case "Long":
                    return jsonObject.getLong(dataSourceField);
                case "String":
                    return jsonObject.getString(dataSourceField);
                case "list":
                    break;
                case "set":
                    break;
                case "map":
                    break;
                default:

            }
        }

        return null;
    }

    @Override
    public ResultDataDto generateSimple(DataFactoryRequestBean dataFactoryRequestBean) throws Exception {
        randomThreadLocal.set(new Random());
        List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList = dataFactoryRequestBean.getDataFactoryRequestFieldBeanList();
        List<Map<String,Object>> batchResultList = new ArrayList<>(dataFactoryRequestBean.getGenerateCount()*2);
        ResultDataDto resultDataDto = new ResultDataDto();

        for (int i = 0;i < dataFactoryRequestBean.getGenerateCount();i++){
            Map<String,Object> fieldValueMap = new HashMap<>(dataFactoryRequestFieldBeanList.size());
            for (DataFactoryRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList){
                Object object = getRandomValue(dataFactoryRequestFieldBean,randomThreadLocal.get());
                fieldValueMap.put(dataFactoryRequestFieldBean.getDataSourceField(),object);
            }
            batchResultList.add(fieldValueMap);
        }
        resultDataDto.setData(batchResultList);
        return resultDataDto;
    }
}
