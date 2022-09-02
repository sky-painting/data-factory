package com.tianhua.datafactory.core.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.HttpApiRequestBO;
import com.tianhua.datafactory.domain.enums.ReturnTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class ResponseFactory {

    private List<Long> getListLong(String response){
        return JSON.parseArray(response,Long.class);
    }

    private List<Integer> getListInteger(String response){
        return JSON.parseArray(response,Integer.class);
    }

    private List<String> getListString(String response){
        return JSON.parseArray(response,String.class);
    }

    private ResultDataDto getResultDataDto(String response){
        return JSON.parseObject(response,ResultDataDto.class);
    }

    /**
     *
     * @param responseStr
     * @param httpApiRequestBO
     * @return
     */
    public List<Map<String,Object>> getResponseListMap(String responseStr, HttpApiRequestBO httpApiRequestBO){
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(httpApiRequestBO.getReturnType().equals(ReturnTypeEnum.LIST_LONG.getType())){
            List<Long> responseList = getListLong(responseStr);
            String key = httpApiRequestBO.getParamFieldList().get(0);
            responseList.stream().forEach(value-> resultList.add(Maps.newHashMap(key,value)));
        }
        if(httpApiRequestBO.getReturnType().equals(ReturnTypeEnum.LIST_STRING.getType())){
            List<String> responseList = getListString(responseStr);
            String key = httpApiRequestBO.getParamFieldList().get(0);
            responseList.stream().forEach(longValue-> resultList.add(Maps.newHashMap(key,longValue)));
        }

        if(httpApiRequestBO.getReturnType().equals(ReturnTypeEnum.LIST_INTEGER.getType())){
            List<Integer> responseList = getListInteger(responseStr);
            String key = httpApiRequestBO.getParamFieldList().get(0);
            responseList.stream().forEach(value-> resultList.add(Maps.newHashMap(key,value)));
        }

        if(httpApiRequestBO.getReturnType().equals(ReturnTypeEnum.RESULT_DTO.getType())) {
            ResultDataDto resultDataDto = getResultDataDto(responseStr);
            if(!resultDataDto.isSuccess()){
                return resultList;
            }
            List responseDataList = (List) resultDataDto.getData();
            responseDataList.stream().forEach(obj -> {
                try {
                    JSONObject jsonObject = (JSONObject) obj;
                    Map<String,Object> objectMap = getObjMap(jsonObject, httpApiRequestBO.getParamFieldList());
                    resultList.add(objectMap);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return resultList;
    }


    /**
     * 根据对应的属性key找到匹配的值map
     * @param jsonObject
     * @param paramFieldList
     * @return
     */
    private Map<String,Object> getObjMap(JSONObject jsonObject, List<String> paramFieldList){
        Map<String,Object> map = new HashMap<>();
        for (String fieldName : paramFieldList){
            map.put(fieldName, jsonObject.get(fieldName));
        }
        return map;
    }


}
