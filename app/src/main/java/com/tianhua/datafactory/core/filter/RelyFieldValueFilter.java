package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description
 * date: 2022/8/20
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "relyFieldValueFilter")
public class RelyFieldValueFilter implements DataFilter {
    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap, List<Map<String, Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }
        String relyField = dataBuildRequestFieldRuleBO.getRelyField();

        Object relyFieldValue = valueMap.get(relyField);
        if(!Objects.isNull(relyFieldValue)){
            valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValue);
        }


    }
}
