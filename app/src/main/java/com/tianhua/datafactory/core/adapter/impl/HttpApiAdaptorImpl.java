package com.tianhua.datafactory.core.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.core.adapter.HttpApiAdapter;
import com.tianhua.datafactory.core.adapter.ResponseFactory;
import com.tianhua.datafactory.core.utils.HttpUtils;
import com.tianhua.datafactory.domain.bo.HttpApiRequestBO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "httpApiAdaptorImpl")
@Slf4j
public class HttpApiAdaptorImpl implements HttpApiAdapter {


    @Autowired
    private ResponseFactory responseFactory;

    @Override
    public List<Map<String, Object>> getServiceDataFromHttp(HttpApiRequestBO httpApiRequestBO) {

        //解析url
        if (httpApiRequestBO.getUrl().contains("/{")){
            int firstIndex = httpApiRequestBO.getUrl().indexOf("{");
            int secondIndex = httpApiRequestBO.getUrl().lastIndexOf("}");
            String restParamStr = httpApiRequestBO.getUrl().substring(firstIndex + 1, secondIndex);
            if (restParamStr.contains("/")) {
                String [] params = restParamStr.split("/");
                for (String param : params){
                    String tmpParam = param;
                    if(tmpParam.contains("{")){
                        tmpParam = tmpParam.replace("{","");
                    }
                    if(tmpParam.contains("}")){
                        tmpParam = tmpParam.replace("}","");
                    }
                    String value = "null";
                    if(httpApiRequestBO.getParams().get(tmpParam) != null){
                        value = httpApiRequestBO.getParams().get(tmpParam).toString();
                    }
                    String url = httpApiRequestBO.getUrl().replace("{"+restParamStr+"}",value);
                    httpApiRequestBO.setUrl(url);
                }
                httpApiRequestBO.setParams(new HashMap<>());
            }else {
                String value = httpApiRequestBO.getParams().get(restParamStr).toString();
                String url = httpApiRequestBO.getUrl().replace("{"+restParamStr+"}",value);
                httpApiRequestBO.setUrl(url);
                httpApiRequestBO.setParams(new HashMap<>());
            }
        }

        try {
            String jsonStr = HttpUtils.doGet(httpApiRequestBO.getUrl(),httpApiRequestBO.getParams());
            return responseFactory.getResponseListMap(jsonStr, httpApiRequestBO);
        }catch (Exception e){
            log.error("请求http接口报错",e);
        }
        return null;

    }


}
