package com.coderman.tianhua.datafactory.core.adaptor.impl;

import com.coderman.tianhua.datafactory.core.adaptor.NacosDataAdaptor;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.kvpair.KVParentPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: NacosDataAdaptorImpl <br>
 * date: 2020/12/16 23:53 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class NacosDataAdaptorImpl implements NacosDataAdaptor {

    @Override
    public List<Map<String, String>> getNacosDataMap(String dataContent, String jsonTemplate) {
        List<Map<String, String>> mapList = new ArrayList<>();
        String[] array = dataContent.split("\n");

        //默认去掉第一行数据
        for (int i = 1; i < array.length; i++) {
            String[] kvArr = array[i].replace(" ", "").replace("\r", "").split(",");
            String[] jsonFieldArr = jsonTemplate.split(",");
            Map<String, String> map = new HashMap<>();
            for ( int j = 0;j < kvArr.length && j< jsonFieldArr.length;j++){
                map.put(jsonFieldArr[j],kvArr[j]);
            }
            mapList.add(map);
        }
        return mapList;
    }


    @Override
    public List<KVPair<String, String>> getNacosDataKV(String dataContent) {
        String[] array = dataContent.split("\n");
        List<KVPair<String, String>> kvPairList = new ArrayList<>();

        //默认去掉第一行数据
        for (int i = 1; i < array.length; i++) {
            String[] kvArr = array[i].replace(" ", "").replace("\r", "").split(",");
            kvPairList.add(KVPair.build(kvArr[1], kvArr[0]));
        }
        return kvPairList;
    }

}
