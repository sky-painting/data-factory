package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.ability.KVPairService;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * Description
 * 对接本地kv enum
 * date: 2022/7/31
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "dataGenerateLocalKVImpl")
@Slf4j
public class DataGenerateLocalKVImpl implements DataGenerateService {
    @Autowired
    private KVPairService kvPairService;

    public Random random = new SecureRandom();

    @Override
    public Object getRandomData(DataSourceFieldRequestBean dataSourceFieldRequestBean) {
        String sourceCode = dataSourceFieldRequestBean.getDataBuildRequestFieldBO().getDataSourceCode();
        String groupKey = sourceCode;
        if(!groupKey.contains("#")){
            log.error("本地kv对应的sourceCode格式错误.");
            //todo throw exception
        }
        String [] array = groupKey.split("#");

        List<KVPairBO> list = kvPairService.getList(KVPairBO.instance().groupKey(array[0]).Key(array[1]));
        int index = random.nextInt(list.size());

        return list.get(index).getValue();
    }
}
