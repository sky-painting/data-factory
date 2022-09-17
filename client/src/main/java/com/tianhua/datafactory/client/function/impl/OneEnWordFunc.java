package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.service.FileDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.List;

/**
 * Description
 *
 * todo 先简单实现，后面加入缓存逻辑
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "oneEnWordFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.ONE_ENGLISH_WORD)
public class OneEnWordFunc implements Function {

    private Logger logger = LoggerFactory.getLogger(OneEnWordFunc.class);
    @Autowired
    private FileDataService fileDataService;

    private SecureRandom secureRandom = new SecureRandom();

    @Override
    public Object createOneData(String... params) {
        List<Object> commentList = fileDataService.getFileDataList(FileDataEnums.EN_WORD.getFileName());
        if(CollectionUtils.isEmpty(commentList)){
            logger.error("数据文件内容读取为空,请检查,fileName = {}",FileDataEnums.EN_WORD.getFileName());
            return null;
        }
        return commentList.get(secureRandom.nextInt(commentList.size()));
    }
}
