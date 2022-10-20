package com.tianhua.datafactory.domain.ability;

import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class CollectionFactory {

    private static SecureRandom random = new SecureRandom();

    /**
     * 构建list数据
     * @param valueArray
     * @param randomCount
     * @param realType
     * @return
     */
    public List buildListValues(String [] valueArray, int randomCount, String realType){
        List valueList = new ArrayList<>();

        //处理List<Integer>
        if(JavaFieldTypeEnum.isInt(realType)){
            for (int i = 0;i < randomCount;i++){
                valueList.add(Integer.parseInt(valueArray[random.nextInt(valueArray.length)]));
            }
        }

        //处理List<String>
        else if(JavaFieldTypeEnum.isString(realType)){
            for (int i = 0;i < randomCount;i++){
                valueList.add(valueArray[random.nextInt(valueArray.length)]);
            }
        }

        //处理List<Long>
        else if(JavaFieldTypeEnum.isLong(realType)){
            for (int i = 0;i < randomCount;i++){
                valueList.add(Long.parseLong(valueArray[random.nextInt(valueArray.length)]));
            }
        }
        return valueList;
    }

    /**
     * 构建set数据
     * @param valueArray
     * @param randomCount
     * @param realType
     * @return
     */
    public Set buildSetValues(String [] valueArray, int randomCount, String realType){
        //处理Set<Integer>
        Set set = new HashSet();

        if(JavaFieldTypeEnum.isInt(realType)){
            for (int i = 0;i < randomCount;i++){
                set.add(Integer.parseInt(valueArray[random.nextInt(valueArray.length)]));
            }
        }

        //处理Set<String>
        else if(JavaFieldTypeEnum.isString(realType)){
            for (int i = 0;i < randomCount;i++){
                set.add(valueArray[random.nextInt(valueArray.length)]);
            }
        }

        //处理Set<Long>
        else if(JavaFieldTypeEnum.isLong(realType)){
            for (int i = 0;i < randomCount;i++){
                set.add(Long.parseLong(valueArray[random.nextInt(valueArray.length)]));
            }
        }

        return set;
    }

}
