package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.utils.response.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * description: DataGenerateRemoteServiceImpl <br>
 * date: 2021/1/17 20:29 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateRemoteServiceImpl")
@Slf4j
public class DataGenerateRemoteServiceImpl implements DataGenerateService {

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean ) {
        String dataSourceCode = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean().getDataSourceCode();
//从远程数据工厂-数据源获取数据
        ResultDataDto<String> resultDataDto = null;
        try {
            resultDataDto = dataSourceService.getDataSourceDetail(dataSourceFieldRequestBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultDataDto == null || !resultDataDto.isSuccess()) {
            log.error("从数据工厂-数据源获取数据 = {}", JSON.toJSONString(resultDataDto));
            return null;
        }

        String jsonValue = resultDataDto.getData();
        if(StringUtils.isEmpty(jsonValue)){
            return null;
        }

        //通过json字段解析，这里提供的数据源必须是数组形式
        JSONArray jsonArray = JSONObject.parseArray(jsonValue);
        DataBuildRequestFieldBean dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean();
        String dataSourceField = dataFactoryRequestFieldBean.getDataSourceField();
        //数组循环解析
        if (dataSourceField.contains("\\.")) {

        } else {
            SecureRandom secureRandom = dataSourceFieldRequestBean.getRandom();
            int index = secureRandom.nextInt(jsonArray.size());
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            String fieldType = dataFactoryRequestFieldBean.getFieldTypeStr();
            switch (fieldType) {
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
}
