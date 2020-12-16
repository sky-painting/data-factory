package com.coderman.tianhua.datafactory.core.adaptor;

import com.coderman.utils.kvpair.KVPair;

import java.util.List;

/**
 * description: ServiceApiAdaptor <br>
 * date: 2020/12/7 23:50 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface ServiceApiAdaptor {
    /**
     * 获取城市数据
     * @return
     */
    List<KVPair<String,String>> getCityList();

    /**
     * 获取省份数据
     * @return
     */
    List<KVPair<String,String>> getProinvceList();
}
