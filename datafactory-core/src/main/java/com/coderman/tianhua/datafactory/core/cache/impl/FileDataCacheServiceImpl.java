package com.coderman.tianhua.datafactory.core.cache.impl;

import com.coderman.tianhua.datafactory.core.cache.FileDataCacheService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * description: FileDataCacheServiceImpl <br>
 * date: 2020/12/16 0:05 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 本地缓存实现--caffeine 实战
 */
@Service
public class FileDataCacheServiceImpl implements FileDataCacheService {
    private static final String FACTORY_PATH = "defaultfactory";

    // 初始化缓存
    private Cache<String, String> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();
    @Override
    public String getCacheSource(String fileName) throws IOException {
        String value = manualCache.getIfPresent(fileName);
        if(StringUtils.isEmpty(value)){
            value = getFileContent(fileName);
        }else {
            return value;
        }
        if(StringUtils.isEmpty(value)){
            return null;
        }else {
            manualCache.put(fileName,value);
        }
        return value;
    }

    /**
     * 获取数据工厂文件内容
     * @param fileName
     * @return
     * @throws IOException
     */
    public String getFileContent(String fileName) throws IOException {
        // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
        String path = FACTORY_PATH+"/"+fileName+".txt";
        ClassPathResource classPathResource = new ClassPathResource(path);

        // 获得File对象，当然也可以获取输入流对象
        File file = classPathResource.getFile();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

}
