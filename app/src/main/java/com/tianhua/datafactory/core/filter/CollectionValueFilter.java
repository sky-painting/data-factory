package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Description
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
                valueList.add(array[random.nextInt(array.length)]);
            }
            valueMap.put(dataBuildRequestFieldBO.getFieldName(),valueList);
            return;
        }

    }
}
