package com.tianhua.datafactory.domain.factory;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 * date: 2022/8/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class CollectionValueConvertFactory {

    public List convert2List(String valueList,Integer fieldType){
        valueList = valueList.trim();
        String [] array = valueList.split(",");
        for (String value : array){

        }
        return null;

    }

}
