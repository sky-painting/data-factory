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
        String jsonStr = HttpUtils.doGet(httpApiRequestBO.getUrl(),httpApiRequestBO.getParams());
        return responseFactory.getResponseListMap(jsonStr, httpApiRequestBO);

    }


}
