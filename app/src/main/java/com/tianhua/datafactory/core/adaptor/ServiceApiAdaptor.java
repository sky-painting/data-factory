package com.tianhua.datafactory.core.adaptor;

import java.util.List;
import java.util.Map;

/**
 * description: ServiceApiAdaptor <br>
 * date: 2020/12/7 23:50 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface ServiceApiAdaptor {
    /**
     * 动态获取服务类--api数据
     * @param url    url
     * @param params 参数
     * @return
     */
    List<String> getServiceDataFromHttp(String url, Map<String, Object> params);
}
