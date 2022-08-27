package com.tianhua.datafactory.core.specification;

import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description
 * date: 2022/8/21
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class TypeConvertFactory {

    /**
     * 将字符串的数据转换成对应数据类型的数据
     * @param valueList
     * @param valueType
     * @return
     */
    public List convertStringToList(String valueList,String valueType ){
        if(StringUtils.isEmpty(valueList) || StringUtils.isEmpty(valueType)){
            return Lists.newArrayList();
        }
        String valueListStr = valueList;
        if(valueList.startsWith("{")){
            valueListStr = valueList.replace("{","").replace("}","");
        }

        if(valueList.startsWith("[")){
            valueListStr = valueList.replace("[","").replace("]","");
        }

        List list = new ArrayList<>();
        String [] valueArr = valueListStr.split(",");
        for (String str : valueArr){
            if(valueType.equals(JavaFieldTypeEnum.INTEGER.getType())){
                list.add(Integer.parseInt(str));
            }
            else if(valueType.equals(JavaFieldTypeEnum.LONG.getType())){
                list.add(Long.parseLong(str));
            }
            else if(valueType.equals(JavaFieldTypeEnum.SHORT.getType())){
                list.add(Short.parseShort(str));
            }
            else if(valueType.equals(JavaFieldTypeEnum.STRING.getType())){
                list.add(str);
            }

            else if(valueType.equals(JavaFieldTypeEnum.DOUBLE.getType())){
                list.add(Double.parseDouble(str));
            }
            else if(valueType.equals(JavaFieldTypeEnum.FLOAT.getType())){
                list.add(Float.parseFloat(str));
            }

        }
        return list;
    }


    /**
     * 将字符串的数据转换成对应数据类型的数据
     * @param valueList
     * @param valueType
     * @return
     */
    public Set convertStringToSet(String valueList, String valueType ){
        if(StringUtils.isEmpty(valueList)){
            return Sets.newHashSet();
        }

        String valueListStr = valueList;
        if(valueList.startsWith("{")){
            valueListStr = valueList.replace("{","").replace("}","");
        }

        if(valueList.startsWith("[")){
            valueListStr = valueList.replace("[","").replace("]","");
        }

        Set set = new HashSet();
        String [] valueArr = valueListStr.split(",");
        for (String str : valueArr){
            if(valueType.equals(JavaFieldTypeEnum.INTEGER.getType())){
                set.add(Integer.parseInt(str));
            }
            if(valueType.equals(JavaFieldTypeEnum.LONG.getType())){
                set.add(Long.parseLong(str));
            }
            if(valueType.equals(JavaFieldTypeEnum.SHORT.getType())){
                set.add(Short.parseShort(str));
            }
            if(valueType.equals(JavaFieldTypeEnum.STRING.getType())){
                set.add(str);
            }

            if(valueType.equals(JavaFieldTypeEnum.DOUBLE.getType())){
                set.add(Double.parseDouble(str));
            }
            if(valueType.equals(JavaFieldTypeEnum.FLOAT.getType())){
                set.add(Float.parseFloat(str));
            }
        }
        return set;
    }



}
