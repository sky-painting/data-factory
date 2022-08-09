package com.tianhua.datafactory.client.function.factory;

import com.tianhua.datafactory.client.context.DataSourceFunctionContext;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description: FunctionFactoryImpl <br>
 * date: 2020/12/31 23:27 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class FunctionFactoryImpl implements FunctionFactory {
    @Resource
    private DataSourceFunctionContext dataSourceFunctionContext;
    @Override
    public Function createFunction(String dataSourceCode) {
        String beanName = dataSourceFunctionContext.getBeanName(dataSourceCode);
        if(StringUtils.isNotEmpty(beanName)){
            return (Function) SpringContextUtil.getBean(beanName);
        }
        return null;
    }
}
