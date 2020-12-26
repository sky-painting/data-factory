package com.coderman.tianhua.datafactory.core.adaptor;

import com.coderman.tianhua.datafactory.core.bean.DataSourceBean;
import com.coderman.utils.kvpair.KVPair;

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
     * @param dataSourceBean
     * @return list<jsonstr>
     */
   List<String> getServiceData(DataSourceBean dataSourceBean);
}
