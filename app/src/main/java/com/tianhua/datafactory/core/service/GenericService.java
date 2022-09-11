package com.tianhua.datafactory.core.service;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * date: 2022/8/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class GenericService {

    /**
     * 获取属性类型中的泛型对象
     *
     * List<XxxBO>
     * List<List<XxxBO>>
     * @param fieldType
     * @return
     */
    public GenericTypeBO getGenericType(String fieldType){
        GenericTypeBO genericTypeBO = new GenericTypeBO();
        if(!fieldType.contains("<")){
            genericTypeBO.setRealType(fieldType);
            return genericTypeBO;
        }


        if(fieldType.contains(",")){
            String [] fieldTypeArr = fieldType.split(",");
            String keyWrapper = fieldTypeArr[0];
            String valueWrapper = fieldTypeArr[1];
            genericTypeBO.setWrapType(keyWrapper.split("<")[0]);
            genericTypeBO.setRealKeyType(keyWrapper.split("<")[1]);
            if(valueWrapper.contains(">>")){
                genericTypeBO.setRealValueType(valueWrapper.replace(">>", ">"));
            }else {
                genericTypeBO.setRealValueType(valueWrapper.replace(">", ""));

            }
            return genericTypeBO;
        }

        String [] array = fieldType.split("<");
        if(array.length == 2){
            genericTypeBO.setWrapType(array[0]);
            genericTypeBO.setRealType(array[1].replace(">",""));
            return genericTypeBO;
        }

        if(array.length == 3){
            GenericTypeBO realTypeBO = new GenericTypeBO();
            realTypeBO.setRealType(array[2].replace(">",""));
            realTypeBO.setWrapType(array[1]);
            GenericTypeBO superTypeBO = new GenericTypeBO();
            superTypeBO.setRealType(array[1] + "<" + array[2].replace(">","")+">");
            superTypeBO.setWrapType(array[1]);
            Map<String, GenericTypeBO> genericTypeBOMap = new HashMap<>();
            genericTypeBOMap.put("realType",realTypeBO);
            superTypeBO.setSubGenericTypeMap(genericTypeBOMap);
            return superTypeBO;
        }

        return genericTypeBO;
    }

    public static void main(String[] args) {
        GenericService genericService = new GenericService();
        System.out.println(JSON.toJSONString(genericService.getGenericType("List<XxxBO>")));
        System.out.println(JSON.toJSONString(genericService.getGenericType("List<List<List<List<XxxBO>>>>")));

        System.out.println(JSON.toJSONString(genericService.getGenericType("Map<String,XxxBO>")));
        System.out.println(JSON.toJSONString(genericService.getGenericType("Map<String,List<XxxBO>>")));

    }

}
