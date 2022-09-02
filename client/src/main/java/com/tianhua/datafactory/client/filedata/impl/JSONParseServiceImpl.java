package com.tianhua.datafactory.client.filedata.impl;

import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.filedata.CommonParseService;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
public class JSONParseServiceImpl implements CommonParseService {
    @Override
    public List<Map<String, Object>> parseData(FileDataSourceContext fileDataSourceContext) throws IOException {


        return null;
    }
}
