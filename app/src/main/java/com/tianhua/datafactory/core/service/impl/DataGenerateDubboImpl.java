package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.adapter.DubboServiceFactory;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import org.springframework.stereotype.Service;

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
public class DataGenerateDubboImpl  implements DataGenerateService {
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        DataSourceBO dataSourceBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceBO();

        DubboServiceFactory.getInstance().genericInvoke(dataSourceBO.getUrl(),dataSourceBO.getProviderService(),null);

        return null;
    }
}
