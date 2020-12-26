package com.coderman.tianhua.datafactory.core.adaptor.impl;

import com.coderman.tianhua.datafactory.core.adaptor.ServiceApiAdaptor;
import com.coderman.tianhua.datafactory.core.bean.DataSourceBean;
import com.coderman.utils.response.ResultDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public List<Map<String, Object>> getServiceData(DataSourceBean dataSourceBean) {
        //
        // post接口？dubbo接口？
        ResultDataDto resultDataDto = restTemplate.getForObject(dataSourceBean.getUrl() , ResultDataDto.class);

        return null;
    }
}
