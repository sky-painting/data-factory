package com.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.core.adapter.DubboServiceFactory;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

/**
 * Description
 * 对接dubbo rpc 泛华调用
 * date: 2022/7/31
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateDubboImpl")
@Slf4j
public class DataGenerateDubboImpl  implements DataGenerateService {
    private Random random = new SecureRandom();
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataSourceBO dataSourceBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceBO();
        String [] urlArr = dataSourceBO.getUrl().split("#");
        Object result = DubboServiceFactory.getInstance().genericInvoke(dataSourceBO.getProviderService(),urlArr[0],urlArr[1],null);

        String dataSourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();
        String targetField = dataSourceCode.split("#")[1];

        List resultList = new ArrayList<>();
        if(result instanceof HashMap){
            HashMap rootMap = (HashMap)result;
            List childList = (List)rootMap.get("data");
            if(childList == null){
                log.warn("查不到dubbo接口数据源..................");
                return null;
            }
            childList.stream().forEach(val ->{
                HashMap valueMap = (HashMap)val;
                resultList.add(valueMap.get(targetField));
            });
        }else if(result instanceof ArrayList){
            ArrayList rootList = (ArrayList)result;
            rootList.stream().forEach(val ->{
                HashMap valueMap = (HashMap)val;
                resultList.add(valueMap.get(targetField));
            });
        }

        return resultList.get(random.nextInt(resultList.size()));
    }
}
