package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
public class CollectionValueFilter implements DataFilter {
    private Random random = new SecureRandom();
    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap, List<Map<String, Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }

        String relyListField = dataBuildRequestFieldRuleBO.getRelyListField();
        String fieldType = dataBuildRequestFieldBO.getFieldType();

        //处理list依赖
        if(StringUtils.isNotEmpty(relyListField)){
            //依赖的是另外一个属性的集合值，随机取部分数据做集合数据
            if(relyListField.startsWith("$") && fieldType.contains("List")){
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



    }
}
