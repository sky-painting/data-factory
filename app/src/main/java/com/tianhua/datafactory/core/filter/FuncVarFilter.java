package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description
 * 如果当前属性依赖另外一个属性的值同时作为内置函数的参数的话需要先过滤
 * 然后再在内置函数使用的时候传入值
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "funcVarFilter")
public class FuncVarFilter implements DataFilter {
    @Override
    public void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> valueMap,  List<Map<String,Object>> list) {
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO();
        if (dataBuildRequestFieldRuleBO == null){
            return;
        }

        String funcVar = dataBuildRequestFieldRuleBO.getFuncVar();
        if(StringUtils.isNotEmpty(funcVar) && funcVar.startsWith("$")){
            String funcField = funcVar.replaceFirst("\\$","");
            Object funcFieldValue = valueMap.get(funcField);
            if(funcFieldValue == null){
                return;
            }
            dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO().setFuncVar(funcFieldValue.toString());
        }


    }
}
