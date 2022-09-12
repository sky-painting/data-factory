package com.tianhua.datafactory.core.filter;

import com.tianhua.datafactory.client.utils.SpringContextUtil;
import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FilterExecutor {

    /**
     * 在获取变量之前执行
     *
     * @param dataBuildRequestFieldBO
     * @param fieldValueMap
     * @param batchResultList
     */
    public void exeFilterBefore(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> fieldValueMap, List<Map<String, Object>> batchResultList){
        List<DataFilter> dataFilterList = SpringContextUtil.getBeanOfType(DataFilter.class);
        for (DataFilter dataFilter : dataFilterList){
            Order order = dataFilter.getClass().getAnnotation(Order.class);
            if(order.value() < 0){
                dataFilter.dataFilt(dataBuildRequestFieldBO, fieldValueMap, batchResultList);
            }
        }
    }

    /**
     * 在获取变量之后执行
     * @param dataBuildRequestFieldBO
     * @param fieldValueMap
     * @param batchResultList
     */
    public void exeFilterAfter(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> fieldValueMap, List<Map<String, Object>> batchResultList){
        List<DataFilter> dataFilterList = SpringContextUtil.getBeanOfType(DataFilter.class);
        for (DataFilter dataFilter : dataFilterList){
            Order order = dataFilter.getClass().getAnnotation(Order.class);
            if(order.value() > 0){
                dataFilter.dataFilt(dataBuildRequestFieldBO, fieldValueMap, batchResultList);
            }
        }
    }

}
