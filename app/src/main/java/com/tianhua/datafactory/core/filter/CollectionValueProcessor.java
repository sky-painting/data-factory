package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.core.specification.TypeConvertFactory;
import com.tianhua.datafactory.domain.ability.CollectionFactory;
import com.tianhua.datafactory.domain.ability.DataProcessor;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
//1代表要在构建变量之后执行
@Order(value = 1)
public class CollectionValueProcessor implements DataProcessor {
    private Random random = new SecureRandom();

    @Autowired
    private TypeConvertFactory typeConvertFactory;

    @Autowired
    private CollectionFactory collectionFactory;


    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap, List<Map<String, Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }

        String defaultValues = dataBuildRequestFieldRuleBO.getDefaultValues();
        GenericTypeBO genericTypeBO = dataBuildRequestFieldBO.getGenericTypeBO();

        if(StringUtils.isNotEmpty(defaultValues) && !genericTypeBO.isRealTypeModel()){
            String [] array = getDefaultValuesArray(defaultValues);
            int randomSize = random.nextInt(array.length);

            if(JavaFieldTypeEnum.isList(genericTypeBO.getWrapType())){
                List valueList = collectionFactory.buildListValues(array, randomSize, genericTypeBO.getRealType());
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueList);
            }else if(JavaFieldTypeEnum.isSet(genericTypeBO.getWrapType())){
                Set valueSet = collectionFactory.buildSetValues(array, randomSize, genericTypeBO.getRealType());
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueSet);
            }
            return;
        }


        String relyField = dataBuildRequestFieldRuleBO.getRelyField();

        //处理list依赖
        if(StringUtils.isNotEmpty(relyField)){
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyField.startsWith("$") && JavaFieldTypeEnum.isList(genericTypeBO.getWrapType())){
                String relyFieldName = relyField.replace("$","");
                List relyFieldValueList = new ArrayList();
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    relyFieldValueList.add(map.get(relyFieldName));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValueList);
                return;
            }
        }

        //处理set依赖
        if(StringUtils.isNotEmpty(relyField)){
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyField.startsWith("$") && JavaFieldTypeEnum.isSet(genericTypeBO.getWrapType())){
                String relyFieldName = relyField.replace("$","");
                Set relyFieldValueSet = new HashSet();
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    relyFieldValueSet.add(map.get(relyFieldName));
                }
                valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValueSet);
                return;
            }
        }

        String relyKeyField = dataBuildRequestFieldRuleBO.getRelyKeyField();
        String relyValueField = dataBuildRequestFieldRuleBO.getRelyValueField();


        //处理map  先处理简单的数据类型
        if(JavaFieldTypeEnum.isMap(genericTypeBO.getWrapType()) && StringUtils.isNotEmpty(relyKeyField) && StringUtils.isNotEmpty(relyValueField)){
            String keyType = genericTypeBO.getRealKeyType();
            String valueType = genericTypeBO.getRealValueType();

            List keyList = new ArrayList();
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyKeyField.startsWith("$") && JavaFieldTypeEnum.isMap(genericTypeBO.getWrapType())){
                String relyFieldName = relyKeyField.replace("$","");
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    keyList.add(map.get(relyFieldName));
                }
            }else {
                keyList = typeConvertFactory.convertStringToList(relyKeyField, keyType);
            }


            List valueList = new ArrayList();
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyValueField.startsWith("$") && JavaFieldTypeEnum.isMap(genericTypeBO.getWrapType())){
                String relyFieldName = relyValueField.replace("$","");
                int randomCount = random.nextInt(100);
                for (int i = 0;i < randomCount;i ++ ){
                    Map map = list.get(random.nextInt(list.size()));
                    valueList.add(map.get(relyFieldName));
                }
            }else {
                valueList = typeConvertFactory.convertStringToList(relyValueField, valueType);
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


    /**
     * 获取默认值列表
     * @param defaultValues
     * @return
     */
    private String [] getDefaultValuesArray(String defaultValues){
        String valueListStr = defaultValues;
        //依赖的是自己提供的数据集合,支持两种格式
        if(valueListStr.startsWith("{")){
            valueListStr = valueListStr.replace("{","").replace("}","");
        }

        if(valueListStr.startsWith("[")){
            valueListStr = valueListStr.replace("[","").replace("]","");
        }

        return valueListStr.split(",");
    }

}
