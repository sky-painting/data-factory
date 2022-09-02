package com.tianhua.datafactory.client.filedata.impl;

import com.tianhua.datafactory.client.context.FieldIndex;
import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.filedata.CommonParseService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "txtParseServiceImpl")
public class TxtParseServiceImpl implements CommonParseService {

    private Logger logger = LoggerFactory.getLogger(TxtParseServiceImpl.class);

    private static Random random = new Random();

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
        int errorCount = 0;
        if(contentList.size() < fileDataSourceContext.getLoadCount()){
            buildResultList(contentList.subList(1,contentList.size()), fileDataSourceContext, list);
        }else {
            buildResultList(contentList.subList(1,fileDataSourceContext.getLoadCount()), fileDataSourceContext, list);
        }

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
        Map<Integer, FieldIndex> fieldIndexMap = fileDataSourceContext.getFieldIndexBOList().stream().collect(Collectors.toMap(FieldIndex::getIndex,o->o));

        for (String str : contentList){

            String [] array = str.split(fileDataSourceContext.getSplitTag());
            if(array == null || array.length == 0){
                errorCount++;
                continue;
            }
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0;i < array.length;i ++){
                FieldIndex fieldIndex = fieldIndexMap.get(i);
                buildRowMap(fieldIndex,str,rowMap);
            }
            list.add(rowMap);
        }
    }

    /**
     * 收集单个元素
     * @param fieldIndex
     * @param value
     * @param rowMap
     */
    private void buildRowMap(FieldIndex fieldIndex, String value,   Map<String, Object> rowMap){

        if(fieldIndex.isLong()){
            rowMap.put(fieldIndex.getFieldName(), Long.valueOf(value));
        }
        else if(fieldIndex.isInteger()){
            rowMap.put(fieldIndex.getFieldName(), Integer.valueOf(value));
        }

        else if(fieldIndex.isShort()){
            rowMap.put(fieldIndex.getFieldName(), Short.valueOf(value));
        }

        else if(fieldIndex.isString()){
            rowMap.put(fieldIndex.getFieldName(), value);
        }
    }
}
