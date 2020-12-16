package com.coderman.tianhua.datafactory.core.adaptor;

import com.coderman.utils.kvpair.KVPair;

import java.util.List;
import java.util.Map;

/**
 * description: NacosDataAdapotr <br>
 * date: 2020/12/6 22:51 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface NacosDataAdaptor {
    /**
     * 获取nacos数据
     * @param dataId
     * @param groupId
     * @return
     */
    List<Map<String,String>> getNacosDataMap(String dataId, String groupId);
    /**
     * 获取nacos数据
     * @param dataId
     * @param groupId
     * @return
     */
    List<KVPair<String,String>> getNacosDataKV(String dataId, String groupId);

}
