package com.tianhua.datafactory.client.filedata.impl.inner;

import com.tianhua.datafactory.client.cache.KVCacheService;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.filedata.FileReadService;
import com.tianhua.datafactory.client.filedata.InnerParseService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "parseFirstNameServiceImpl")
public class ParseFirstNameServiceImpl implements InnerParseService {
    private static Logger logger = LoggerFactory.getLogger(ParseFirstNameServiceImpl.class);

    @Autowired
    private KVCacheService cacheService;

    @Autowired
    private FileReadService fileReadService;


    @Override
    public List<Object> parseFileData() {
        String dataSourceCode = InnerDataSourceCode.FIRST_NAME;

        List<Object> list = cacheService.getCache(dataSourceCode);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
            File dataFile = fileReadService.getDataFile(FileDataEnums.FIRST_NAME.getFileName());
            if (dataFile == null) {
                logger.error("查不到对应的内置文件数据源:fileName = {}",FileDataEnums.FIRST_NAME.getFileName());
                return null;
            }
            try {
                List<String> fileDataList = FileUtils.readLines(dataFile, "UTF-8");
                for (String str : fileDataList){
                    String[] group = str.split(",");
                    for (String gStr : group) {
                        list.add(gStr);
                    }
                }
                cacheService.putCache(dataSourceCode, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
