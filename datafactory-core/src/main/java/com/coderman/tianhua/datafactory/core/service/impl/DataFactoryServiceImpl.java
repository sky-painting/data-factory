package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coderman.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.coderman.tianhua.datafactory.client.function.Function;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldRuleBean2;

import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.functionfactory.FunctionFactory;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.utils.response.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

    private ThreadLocal<SecureRandom> randomThreadLocal = new ThreadLocal<>();

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private FunctionFactory functionFactory;

    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object getRandomValue(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataFactoryRequestFieldBean dataFactoryRequestFieldBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean();

        String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
        //从默认值中获取数据
        if (StringUtils.isEmpty(dataSourceCode)) {
            if (CollectionUtils.isNotEmpty(dataFactoryRequestFieldBean.getDefaultValueList())) {
                int size = dataFactoryRequestFieldBean.getDefaultValueList().size();
                return dataFactoryRequestFieldBean.getDefaultValueList().get(randomThreadLocal.get().nextInt(size));
            } else {
                //todo 寻找依赖方  支持一对多数据字段存在依赖的情况下的数据生成
            }
        }
        //如果是内置数据源则从内置数据源工厂中的Function中构建随机数据
        if (dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
            Function function = dataSourceFieldRequestBean.getFunction();
            DataFactoryRequestFieldRuleBean2 dataFactoryRequestFieldRuleBean =
                    dataFactoryRequestFieldBean.getDataFactoryRequestFieldRuleBean();
            if (dataFactoryRequestFieldRuleBean == null) {
                return function.createOneData(null, null);
            } else {
                return function.createOneData(dataFactoryRequestFieldRuleBean.getDepencyFunctionMethod(), dataFactoryRequestFieldRuleBean.getDepencyFunctionMethodParam());
            }
        }

        //从远程数据工厂-数据源获取数据
        ResultDataDto<String> resultDataDto = dataSourceService.getDataSourceDetail(dataFactoryRequestFieldBean.getDataSourceCode());
        if (!resultDataDto.isSuccess()) {
            log.error("从数据工厂-数据源获取数据 = {}", JSON.toJSONString(resultDataDto));
            return null;
        }

        String jsonValue = resultDataDto.getData();

        //通过json字段解析，这里提供的数据源必须是数组形式
        JSONArray jsonArray = JSONObject.parseArray(jsonValue);

        String dataSourceField = dataFactoryRequestFieldBean.getDataSourceField();
        //数组循环解析
        if (dataSourceField.contains("\\.")) {

        } else {
            SecureRandom secureRandom = dataSourceFieldRequestBean.getRandom();
            int index = secureRandom.nextInt(jsonArray.size());
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            //
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

    @Override
    public ResultDataDto generateSimple(DataFactoryRequestBean dataFactoryRequestBean) throws Exception {
        randomThreadLocal.set(new SecureRandom());
        List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList = dataFactoryRequestBean.getDataFactoryRequestFieldBeanList();
        List<Map<String, Object>> batchResultList = new ArrayList<>(dataFactoryRequestBean.getGenerateCount() * 2);
        ResultDataDto resultDataDto = new ResultDataDto();

        Map<String, Function> functionMap = new HashMap<>();

        for (DataFactoryRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {
            String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
            if (dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
                Function function = functionFactory.createFunction(dataSourceCode);
                functionMap.put(dataSourceCode,function);
            }
        }

        for (int i = 0; i < dataFactoryRequestBean.getGenerateCount(); i++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataFactoryRequestFieldBeanList.size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();

            for (DataFactoryRequestFieldBean dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {
                dataSourceFieldRequestBean.setFunction(functionMap.get(dataFactoryRequestFieldBean.getDataSourceCode()));
                dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
                dataSourceFieldRequestBean.setDataFactoryRequestFieldBean(dataFactoryRequestFieldBean);
                dataSourceFieldRequestBean.setRandom(randomThreadLocal.get());
                //获取随机字段值
                Object object = getRandomValue(dataSourceFieldRequestBean);
                //todo 依赖数据支持
                fieldValueMap.put(dataFactoryRequestFieldBean.getDataSourceField(), object);
            }
            batchResultList.add(fieldValueMap);
        }
        resultDataDto.setData(batchResultList);
        return resultDataDto;
    }

    /**
     * 对随机数进行后置处理
     *
     * @param factoryRequestFieldBean
     * @param randomValue
     * @return
     */
    private String getPostValueString(DataFactoryRequestFieldBean factoryRequestFieldBean, Object randomValue) {
        DataFactoryRequestFieldRuleBean2 dataFactoryRequestFieldRuleBean2 = factoryRequestFieldBean.getDataFactoryRequestFieldRuleBean();
        String value = randomValue.toString();
        //字符串型随机数据的后置处理
        if (factoryRequestFieldBean.getFieldTypeStr().equals("String")) {
            if (StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean2.getPrefixStr())) {
                value = dataFactoryRequestFieldRuleBean2.getPrefixStr() + value;
            }
            if (StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean2.getSubfixStr())) {
                value = value + dataFactoryRequestFieldRuleBean2.getSubfixStr();
            }
        }
        return value;
    }


}
