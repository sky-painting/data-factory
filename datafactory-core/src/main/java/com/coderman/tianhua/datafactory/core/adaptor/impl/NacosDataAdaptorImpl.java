package com.coderman.tianhua.datafactory.core.adaptor.impl;

import com.coderman.tianhua.datafactory.core.adaptor.NacosDataAdaptor;
import com.coderman.utils.kvpair.KVPair;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public List<KVPair<String, String>> getNacosDataKV(String dataContent) {
        return null;
    }

}
