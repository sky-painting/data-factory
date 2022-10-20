package com.tianhua.datafactory.client.filedata.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.filedata.impl.common.ExcelParseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private Logger logger = LoggerFactory.getLogger(NoModelDataListener.class);

    private Integer skipRow;


    public NoModelDataListener(){}

    public NoModelDataListener(Integer skipRow){
        this.skipRow = skipRow;
    }

    private static final int BATCH_COUNT = 50000;
    private List<Map<Integer, String>> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        int rowIndex = context.readRowHolder().getRowIndex();
        if(rowIndex < skipRow){
            return;
        }

        logger.info("解析到一条数据:{}", JSON.toJSONString(data));
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        logger.info("所有数据解析完成！");
    }

    public List<Map<Integer, String>> getDataList() {
        return dataList;
    }
}
