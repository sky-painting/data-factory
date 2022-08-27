package com.tianhua.datafactory.core.filter;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.core.specification.TypeConvertFactory;
import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

/**
 * Description
 *
 * 集合类的数据处理
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "collectionValueFilter")
@Slf4j
public class CollectionValueFilter implements DataFilter {
    private Random random = new SecureRandom();

    @Autowired
    private TypeConvertFactory typeConvertFactory;


    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap, List<Map<String, Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }

        String relyListField = dataBuildRequestFieldRuleBO.getRelyListField();
        String fieldType = dataBuildRequestFieldBO.getFieldType();


        String relySetField = dataBuildRequestFieldRuleBO.getRelySetField();

        String relyMapKeyField = dataBuildRequestFieldRuleBO.getRelyMapKeyField();

        String relyMapValueField = dataBuildRequestFieldRuleBO.getRelyMapValueField();

        //处理list依赖
        if(StringUtils.isNotEmpty(relyListField)){
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyListField.startsWith("$") && fieldType.contains("List<")){
                String relyFieldName = relyListField.replace("$","");
                List relyFieldValueList = new ArrayList();
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    relyFieldValueList.add(map.get(relyFieldName));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValueList);
                return;
            }

            String valueListStr = relyListField;
            //依赖的是自己提供的数据集合,支持两种格式
            if(relyListField.startsWith("{")){
                valueListStr = valueListStr.replace("{","").replace("}","");
            }

            if(relyListField.startsWith("[")){
                valueListStr = valueListStr.replace("[","").replace("]","");
            }

            String [] array = valueListStr.split(",");

            String generics = dataBuildRequestFieldBO.getGenerics();

            //处理List<Integer>
            if(generics.equals(JavaFieldTypeEnum.INTEGER.getType())){
                int randomSize = random.nextInt(array.length);
                List valueList = new ArrayList<>();
                for (int i = 0;i < randomSize;i++){
                    valueList.add(Integer.parseInt(array[random.nextInt(array.length)]));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueList);
            }

            //处理List<String>
            else if(generics.equals(JavaFieldTypeEnum.STRING.getType())){
                int randomSize = random.nextInt(array.length);
                List valueList = new ArrayList<>();
                for (int i = 0;i < randomSize;i++){
                    valueList.add(array[random.nextInt(array.length)]);
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueList);
            }

            //处理List<Long>
            else if(generics.equals(JavaFieldTypeEnum.LONG.getType())){
                int randomSize = random.nextInt(array.length);
                List valueList = new ArrayList<>();
                for (int i = 0;i < randomSize;i++){
                    valueList.add(Long.parseLong(array[random.nextInt(array.length)]));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueList);
            }
        }

        //处理set依赖
        if(StringUtils.isNotEmpty(relySetField)){
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relySetField.startsWith("$") && fieldType.contains("Set<")){
                String relyFieldName = relySetField.replace("$","");
                Set relyFieldValueSet = new HashSet();
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    relyFieldValueSet.add(map.get(relyFieldName));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValueSet);
                return;
            }

            String valueSetStr = relySetField;
            //依赖的是自己提供的数据集合,支持两种格式
            if(relySetField.startsWith("{")){
                valueSetStr = valueSetStr.replace("{","").replace("}","");
            }

            if(relySetField.startsWith("[")){
                valueSetStr = valueSetStr.replace("[","").replace("]","");
            }

            String [] array = valueSetStr.split(",");

            String generics = dataBuildRequestFieldBO.getGenerics();
            Set valueSet = new HashSet();
            int randomSize = random.nextInt(array.length);
            if(randomSize == 0){
                randomSize = 1;
            }
            //处理Set<Integer>
            if(generics.equals(JavaFieldTypeEnum.INTEGER.getType())){
                for (int i = 0;i < randomSize;i++){
                    valueSet.add(Integer.parseInt(array[random.nextInt(array.length)]));
                }
            }

            //处理Set<String>
            else if(generics.equals(JavaFieldTypeEnum.STRING.getType())){
                for (int i = 0;i < randomSize;i++){
                    valueSet.add(array[random.nextInt(array.length)]);
                }
            }

            //处理Set<Long>
            else if(generics.equals(JavaFieldTypeEnum.LONG.getType())){
                for (int i = 0;i < randomSize;i++){
                    valueSet.add(Long.parseLong(array[random.nextInt(array.length)]));
                }
            }

            valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueSet);
        }

        //处理map  先处理简单的数据类型
        if(fieldType.contains("Map<") && StringUtils.isNotEmpty(relyMapKeyField) && StringUtils.isNotEmpty(relyMapValueField)){

            String [] array = fieldType.split(",");
            String keyType = "";
            String valueType = "";
            if(array.length == 2){
                keyType = array[0].replace("Map<","").trim();
                valueType = array[1].replace(">","").trim();
            }


            List keyList = new ArrayList();
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyMapKeyField.startsWith("$") && fieldType.contains("Map<")){
                String relyFieldName = relyMapKeyField.replace("$","");
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    keyList.add(map.get(relyFieldName));
                }
            }else {
                keyList = typeConvertFactory.convertStringToList(relyMapKeyField, keyType);
            }


            List valueList = new ArrayList();
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyMapValueField.startsWith("$") && fieldType.contains("Map<")){
                String relyFieldName = relyMapValueField.replace("$","");
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    valueList.add(map.get(relyFieldName));
                }
            }else {
                valueList = typeConvertFactory.convertStringToList(relyMapValueField, valueType);
            }

            Map map = new HashMap<>();
            if(keyList.size() > 0 && valueList.size() > 0){
                int randomSize = random.nextInt(keyList.size());
                if(randomSize == 0){
                    randomSize = 1;
                }
                for (int i = 0;i < randomSize;i++){
                    map.put(keyList.get(random.nextInt(keyList.size())), valueList.get(random.nextInt(valueList.size())));
                }
            }

            valueMap.put(dataBuildRequestFieldBO.getFieldName(),map);

        }


    }
}
