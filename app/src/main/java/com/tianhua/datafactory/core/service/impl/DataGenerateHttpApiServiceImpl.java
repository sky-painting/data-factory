package com.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * description: DataGenerateRemoteServiceImpl <br>
 * date: 2021/1/17 20:29 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "dataGenerateHttpApiServiceImpl")
@Slf4j
public class DataGenerateHttpApiServiceImpl implements DataGenerateService {

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean ) {

        return null;
    }
}
