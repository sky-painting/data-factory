package com.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.core.adapter.HttpApiAdapter;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.HttpApiRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.domain.enums.ReturnTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * description: DataGenerateRemoteServiceImpl <br>
 * date: 2021/1/17 20:29 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateHttpApiServiceImpl")
@Slf4j
public class DataGenerateHttpApiServiceImpl implements DataGenerateService {

    @Autowired
    private HttpApiAdapter httpApiAdapter;

    /**
     * 初始化缓存，key:dataSourceCode数据源编码
     * value:对应的数据源值列表
     */
    private static final Cache<String, List<Object>> manualCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10 * 60, TimeUnit.SECONDS)
            .build();

    private static  SecureRandom secureRandom = new SecureRandom();

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {

        DataSourceBO dataSourceBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceBO();

        String dataSourceCode = dataSourceBO.getSourceCode();
        if(!dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode + "#" + dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getFieldName();
        }

        List<Object> list = manualCache.getIfPresent(dataSourceCode);
        if(list != null && list.size() > 0){
            return list.get(secureRandom.nextInt(list.size()));
        }

        Object value = initCache(dataSourceFieldRequestBean);
        return value;
    }

    /**
     * 缓存接口响应数据
     * @param dataSourceFieldRequestBean
     * @return
     */
    private synchronized Object  initCache(DataSourceFieldRequestBean dataSourceFieldRequestBean){
        DataSourceBO dataSourceBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceBO();

        HttpApiRequestBO httpApiRequestBO = new HttpApiRequestBO(dataSourceBO.getProviderDomainUrl() + dataSourceBO.getUrl());
        httpApiRequestBO.setServiceName(dataSourceBO.getProviderService());

        httpApiRequestBO.setReturnType(dataSourceBO.getStructType());

        if(dataSourceBO.getDataSourceReqConfigList() != null){
            for (DataSourceReqConfigBO dataSourceReqConfigBO : dataSourceBO.getDataSourceReqConfigList()){
                httpApiRequestBO.addParam(dataSourceReqConfigBO.getParamKey(),dataSourceReqConfigBO.getParamValue());
            }
        }


        for (DataSourceRespConfigBO dataSourceRespConfigBO : dataSourceBO.getDataSourceRespConfigList()){
            httpApiRequestBO.addParamField(dataSourceRespConfigBO.getFieldKey());
        }

        List<Map<String,Object>> dataList = httpApiAdapter.getServiceDataFromHttp(httpApiRequestBO);
        if(CollectionUtils.isEmpty(dataList)){
            return null;
        }

        Map<String,List<Object>> responseMap = new HashMap<>();


        String dataSourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();

        if(dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode.split("#")[0];
        }

        String finalDataSourceCode = dataSourceCode;
        dataList.forEach(map->{
            for (DataSourceRespConfigBO dataSourceRespConfigBO : dataSourceBO.getDataSourceRespConfigList()){
                String subDataSourceCode = finalDataSourceCode + "#" +dataSourceRespConfigBO.getFieldKey();
                List<Object> list = responseMap.get(subDataSourceCode);
                if(list == null){
                    list = new ArrayList<>();
                }

                Object fieldValue = map.get(dataSourceRespConfigBO.getFieldKey());
                if (fieldValue != null) {
                    list.add(fieldValue);
                }
                responseMap.put(subDataSourceCode, list);
            }
        });


        responseMap.forEach((k,v)-> manualCache.put(k, v));

        String subDataSourceCode = finalDataSourceCode + "#" +dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getFieldName();
        List<Object> list = manualCache.getIfPresent(subDataSourceCode);
        if(list != null && list.size() > 0){
            return list.get(secureRandom.nextInt(list.size()));
        }

        return null;

    }
}
