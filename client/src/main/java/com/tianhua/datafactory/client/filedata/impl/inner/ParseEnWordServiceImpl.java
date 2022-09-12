package com.tianhua.datafactory.client.filedata.impl.inner;

import com.tianhua.datafactory.client.cache.KVCacheService;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.filedata.FileReadService;
import com.tianhua.datafactory.client.filedata.InnerParseService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "parseEnWordServiceImpl")
public class ParseEnWordServiceImpl  implements InnerParseService {

    private static Logger logger = LoggerFactory.getLogger(ParseEnWordServiceImpl.class);

    @Autowired
    private KVCacheService cacheService;

    @Autowired
    private FileReadService fileReadService;

    @Override
    public List<Object> parseFileData() {
        List<Object> list = new ArrayList<>();

        File dataFile = fileReadService.getDataFile(FileDataEnums.EN_WORD.getFileName());
        if (dataFile == null) {
            logger.error("查不到对应的内置文件数据源:fileName = {}",FileDataEnums.EN_WORD.getFileName());
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
