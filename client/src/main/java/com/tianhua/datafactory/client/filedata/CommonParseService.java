package com.tianhua.datafactory.client.filedata;

import com.tianhua.datafactory.client.context.FileDataSourceContext;

import java.io.IOException;
import java.text.ParseException;
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
public interface CommonParseService {
    /**
     * 从文件中解析数据源
     * @param fileDataSourceContext
     * @return
     * @throws IOException
     */
    List<Map<String,Object>> parseData(FileDataSourceContext fileDataSourceContext) throws IOException, ParseException;
}
