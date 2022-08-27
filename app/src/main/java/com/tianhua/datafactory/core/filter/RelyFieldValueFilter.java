package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
//1代表要在构建变量之后执行
@Order(value = 1)
public class RelyFieldValueFilter implements DataFilter {
    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap, List<Map<String, Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }
        String relyField = dataBuildRequestFieldRuleBO.getRelyField();

        Object relyFieldValue = valueMap.get(relyField);

        if(!Objects.isNull(relyFieldValue) && StringUtils.isEmpty(dataBuildRequestFieldBO.getOriginFieldName())){
            valueMap.put(dataBuildRequestFieldBO.getFieldName(),relyFieldValue);
            return;
        }

        if(!Objects.isNull(relyFieldValue) && StringUtils.isNotEmpty(dataBuildRequestFieldBO.getOriginFieldName())){
            Object originValue = valueMap.get(dataBuildRequestFieldBO.getOriginFieldName());
            if(originValue instanceof HashMap){
                HashMap currentMap = (HashMap) originValue;
                currentMap.put(dataBuildRequestFieldBO.getFieldName(), relyFieldValue);
                valueMap.put(dataBuildRequestFieldBO.getOriginFieldName(), currentMap);
            }
        }

    }
}
