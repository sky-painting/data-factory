package com.tianhua.datafactory.core.adapter.impl;

import com.tianhua.datafactory.core.adapter.NacosDataAdapter;
import com.coderman.utils.kvpair.KVPair;
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
public class NacosDataAdapterImpl implements NacosDataAdapter {

    @Override
    public List<Map<String, String>> getNacosDataMap(String dataContent, String jsonTemplate) {
        List<Map<String, String>> mapList = new ArrayList<>();
        String[] array = dataContent.split("\n");

        for (int i = 0; i < array.length; i++) {
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
        for (int i = 0; i < array.length; i++) {
            String[] kvArr = array[i].replace(" ", "").replace("\r", "").split(",");
            kvPairList.add(KVPair.build(kvArr[0], kvArr[1]));
        }
        return kvPairList;
    }

}
