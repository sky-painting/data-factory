package com.coderman.tianhua.datafactory.core.adaptor.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.core.adaptor.ServiceApiAdaptor;
import com.coderman.tianhua.datafactory.core.bean.DataSourceBean;
import com.coderman.utils.response.ResultDataDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description: ServiceApiAdaptorImpl <br>
 * date: 2020/12/26 23:20 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class ServiceApiAdaptorImpl implements ServiceApiAdaptor {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<String> getServiceData(DataSourceBean dataSourceBean) {
        //
        // post接口？dubbo接口？

        //返回是list处理
        ResultDataDto<List> resultDataDto = restTemplate.getForObject(dataSourceBean.getUrl() , ResultDataDto.class);
        if(!resultDataDto.isSuccess()){
            return null;
        }

        //去除result包装
        List dataList = resultDataDto.getData();
        if(CollectionUtils.isEmpty(dataList)){
            return null;
        }
        List<String> list = new ArrayList<>();
        dataList.stream().forEach(data->{
            list.add(JSON.toJSONString(data));
        });
        return list;
    }
}
