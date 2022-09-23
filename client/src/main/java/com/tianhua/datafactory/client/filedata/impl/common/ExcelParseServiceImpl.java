package com.tianhua.datafactory.client.filedata.impl.common;

import com.alibaba.excel.EasyExcel;
import com.tianhua.datafactory.client.context.FieldIndex;
import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.filedata.AbstractParseService;
import com.tianhua.datafactory.client.filedata.CommonParseService;
import com.tianhua.datafactory.client.filedata.listener.NoModelDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "excelParseServiceImpl")
public class ExcelParseServiceImpl  extends AbstractParseService implements CommonParseService {
    private Logger logger = LoggerFactory.getLogger(ExcelParseServiceImpl.class);

    @Override
    public List<Map<String, Object>> parseData(FileDataSourceContext fileDataSourceContext) throws  ParseException {
        File file = new File(fileDataSourceContext.getFileUrl());
        if(!file.exists()){
            logger.error("文件不存在.");
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        NoModelDataListener modelDataListener = new NoModelDataListener(fileDataSourceContext.getSkipCount());
        EasyExcel.read(file.getAbsolutePath(), modelDataListener).sheet().doRead();
        List<Map<Integer, String>> mapList = modelDataListener.getDataList();


        for (Map<Integer, String> map : mapList){
            Map<String, Object> rowMap = new HashMap<>();
            for (FieldIndex fieldIndex : fileDataSourceContext.getFieldIndexBOList()){
                buildRowMap(fieldIndex, map.get(fieldIndex.getIndex()), rowMap);
            }
            list.add(rowMap);
        }
        return list;
    }

}
