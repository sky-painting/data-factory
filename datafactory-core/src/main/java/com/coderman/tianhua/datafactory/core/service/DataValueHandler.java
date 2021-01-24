package com.coderman.tianhua.datafactory.core.service;

import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldRuleBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * description: DataValueHandler <br>
 * date: 2021/1/22 23:42 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class DataValueHandler {

    /**
     * 对数据前缀后缀进行处理
     * @param o
     * @param dataFactoryRequestFieldRuleBean
     * @return
     */
    public Object handleValue(Object o, DataBuildRequestFieldRuleBean dataFactoryRequestFieldRuleBean){
        String value = o.toString();
        if(dataFactoryRequestFieldRuleBean == null){
            return value;
        }

        if(StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean.getPrefixStr())){
            value = dataFactoryRequestFieldRuleBean.getPrefixStr() + o.toString();
        }
        if(StringUtils.isNotEmpty(dataFactoryRequestFieldRuleBean.getSubfixStr())){
            value = value + dataFactoryRequestFieldRuleBean.getSubfixStr();
        }
        return value;
    }


}
