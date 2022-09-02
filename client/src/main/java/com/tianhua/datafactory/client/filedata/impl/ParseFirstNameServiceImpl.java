package com.tianhua.datafactory.client.filedata.impl;

import com.tianhua.datafactory.client.cache.KVCacheService;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.filedata.FileReadService;
import com.tianhua.datafactory.client.filedata.InnerParseService;
import org.apache.commons.io.FileUtils;
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

    @Autowired
    private KVCacheService cacheService;

    @Autowired
    private FileReadService fileReadService;


    @Override
    public List<String> parseFileData() {
        String fileName = FileDataEnums.FIRST_NAME.getFileName();
        List<String> list = cacheService.getCache(fileName);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
            File dataFile = fileReadService.getDataFile(fileName);
            if (dataFile == null) {
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
                cacheService.putCache(fileName, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
