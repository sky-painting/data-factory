package com.tianhua.datafactory.core.adapter;

import com.tianhua.datafactory.domain.bo.HttpApiRequestBO;

import java.util.List;
import java.util.Map;

/**
 * description: ServiceApiAdaptor <br>
 * date: 2020/12/7 23:50 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface HttpApiAdapter {
    /**
     * http协议获取数据
     * @param httpApiRequestBO
     * @return
     */
    List<Map<String,Object>> getServiceDataFromHttp(HttpApiRequestBO httpApiRequestBO);

}
