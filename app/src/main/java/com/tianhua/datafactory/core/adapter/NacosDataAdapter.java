package com.tianhua.datafactory.core.adapter;

import com.coderman.utils.kvpair.KVPair;

import java.util.List;
import java.util.Map;

/**
 * description: NacosDataAdapotr <br>
 * date: 2020/12/6 22:51 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */

@Deprecated
public interface NacosDataAdapter {
    /**
     * 获取nacos数据--匹配jsonTemplate
     *
     * @param dataContent
     * @param jsonTemplate 多个字符串对应关系,如dataContent是三元组k,v,p，则可以定义jsonTemplate key,value,parentKey
     *                     如果dataContent是多个值组成，则可以定义jsonTemplate 类似a,b,c,d模式
     * @return
     */
    List<Map<String,String>> getNacosDataMap(String dataContent,String jsonTemplate);
    /**
     * 获取nacos数据
     * @param dataContent
     * @return KVPair 形式
     */
    List<KVPair<String,String>> getNacosDataKV(String dataContent);

}
