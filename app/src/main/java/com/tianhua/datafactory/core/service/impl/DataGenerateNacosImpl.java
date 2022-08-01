package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.DataSourceFieldRequestBean;
import org.springframework.stereotype.Service;

/**
 * Description
 * 对接nacos服务
 * date: 2022/7/31
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateNacosImpl")
public class DataGenerateNacosImpl implements DataGenerateService {
    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        //1.获取naocs 链接对象
        //2获取查询group参数
        //3获取数据


        return null;
    }
}
