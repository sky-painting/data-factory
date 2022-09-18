package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.CacheFunction;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

/**
 * Description
 * 一段评论中文
 * todo 先简单实现，后面加入缓存逻辑
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "commentFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.COMMENT)
public class CommentFunc implements Function {
    @Autowired
    private FileDataService fileDataService;

    private SecureRandom secureRandom = new SecureRandom();

    @Override
    public Object createOneData(String... params) throws Exception {

        List<Object> commentList = fileDataService.getFileDataList(FileDataEnums.COMMENT.getFileName());

        return commentList.get(secureRandom.nextInt(commentList.size()));
    }
}
