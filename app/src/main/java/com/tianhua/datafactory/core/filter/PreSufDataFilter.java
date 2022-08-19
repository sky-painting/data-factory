package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "preSufDataFilter")
public class PreSufDataFilter implements DataFilter {
    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap,  List<Map<String,Object>> list) {
        if (dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO() == null){
            return;
        }

        String fieldName = dataBuildRequestFieldBO.getFieldName();
        String prefix = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO().getPrefix();
        String subfix = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO().getSubfix();
        if(valueMap.get(fieldName) == null){
            return;
        }
        String currentValue = valueMap.get(fieldName).toString();
        if(!StringUtils.isEmpty(prefix)){
            currentValue = prefix + currentValue;
        }
        if(!StringUtils.isEmpty(subfix)){
            currentValue = currentValue + subfix;
        }

        valueMap.put(fieldName,currentValue);

    }
}
