/*

package com.tianhua.datafactory.core.adapter.impl;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.core.adapter.HttpApiAdapter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


*/
/**
 * description: ServiceApiAdaptorImpl <br>
 * date: 2020/12/26 23:20 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 *//*


@Service
public class HttpApiAdaptorImpl implements HttpApiAdapter {

    */
/**
     * 服务名---微服务的方式访问
     *//*

  服务名---微服务的方式访问  @Autowired
    private RestTemplate restTemplate;

    */
/**
     * 域名
     * @param url    url
     * @param params 参数
     * @return
     *//*


    @Override
    public List<String> getServiceDataFromHttp(String url,Map<String, Object> params) {
        //
        // 默认get接口 post接口？dubbo接口？

        //返回是list处理
        ResultDataDto<List> resultDataDto = new ResultDataDto();
        try {
             resultDataDto = restTemplate.getForObject(url , ResultDataDto.class);
        }catch (Exception e){
            resultDataDto = restTemplate.postForObject(url,params , ResultDataDto.class);
        }
        //去除result包装
        List dataList = resultDataDto.getData();
        if(CollectionUtils.isEmpty(dataList)){
            return null;
        }
        return dataList;
    }
}

*/
