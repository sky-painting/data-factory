package com.tianhua.datafactory.domain.ability;

import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * 数据过滤处理接口
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface DataProcessor {
    /**
     * 数据过滤接口
     * @param dataBuildRequestFieldBO
     * @param valueMap
     * @param list
     */
    void dataFilt(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String,Object> valueMap, List<Map<String,Object>> list);
}
