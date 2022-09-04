package com.tianhua.datafactory.client.filedata.impl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianhua.datafactory.client.context.FieldIndex;
import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.filedata.CommonParseService;
import com.tianhua.datafactory.client.filedata.AbstractParseService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "jSONParseServiceImpl")
public class JSONParseServiceImpl extends AbstractParseService implements CommonParseService {

    private Logger logger = LoggerFactory.getLogger(JSONParseServiceImpl.class);

    @Override
    public List<Map<String, Object>> parseData(FileDataSourceContext fileDataSourceContext) throws IOException {
        File file = new File(fileDataSourceContext.getFileUrl());
        if(!file.exists()){
            logger.error("文件不存在.");
            return null;
        }

        long size = FileUtils.sizeOf(file);
        //如果是大文件可能需要特殊的处理逻辑来访问数据
        FileUtils.byteCountToDisplaySize(size);
        List<Map<String, Object>> list = new ArrayList<>();

        List<String> contentList = FileUtils.readLines(file,"utf-8");
        if(fileDataSourceContext.getSkipCount() > 0){
            contentList = contentList.subList(fileDataSourceContext.getSkipCount(),contentList.size());
        }
        //获取随机子集合
        List<String> currentList = getRandomList(contentList, fileDataSourceContext.getLoadCount());

        buildResultList(currentList, fileDataSourceContext, list);

        return list;
    }

    /**
     * 解析数据文件内容,
     * @param contentList
     * @param fileDataSourceContext
     * @param list
     */
    private void buildResultList(List<String> contentList, FileDataSourceContext fileDataSourceContext, List<Map<String, Object>> list){
        int errorCount = 0;
        for (String str : contentList){
            JSONObject jsonObject = JSON.parseObject(str);
            if(jsonObject == null){
                errorCount++;
                continue;
            }
            Map<String, Object> rowMap = new HashMap<>();
            for (FieldIndex fieldIndex : fileDataSourceContext.getFieldIndexBOList()){
                buildRowMap(fieldIndex, jsonObject.getString(fieldIndex.getFieldName()), rowMap);
            }
            list.add(rowMap);
        }
        if(errorCount > 0){
            logger.error("解析错误次数:{}",errorCount);
        }
    }




}
