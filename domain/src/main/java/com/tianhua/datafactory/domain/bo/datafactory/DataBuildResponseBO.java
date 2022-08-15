package com.tianhua.datafactory.domain.bo.datafactory;

import lombok.Data;

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
@Data
public class DataBuildResponseBO {
    /**
     * 数据构建请求对象
     */
    private DataBuildRequestBO dataBuildRequestBO;
    /**
     * 数据构建结果集
     */
    private List<Map<String,Object>> resultList;
}
