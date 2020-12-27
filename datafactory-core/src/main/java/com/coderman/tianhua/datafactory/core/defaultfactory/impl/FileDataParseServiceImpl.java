package com.coderman.tianhua.datafactory.core.defaultfactory.impl;

import com.coderman.tianhua.datafactory.core.cache.FileDataCacheService;
import com.coderman.tianhua.datafactory.core.constants.DefaultFileFactory;
import com.coderman.tianhua.datafactory.core.defaultfactory.FileDataParseService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description: FileDataParseServiceImpl <br>
 * date: 2020/12/16 23:28 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class FileDataParseServiceImpl  implements FileDataParseService {
    private static final String FACTORY_PATH = "defaultfactory";

    /**
     * 初始化缓存，key:对应的配置文件名
     * value:对应的配置内容列表
     */
    private final Cache<String, List<String>> manualCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();

    @Override
    public List<String> parseFirstName() throws IOException {
        String fileName = DefaultFileFactory.FIRST_NAME;
        List<String> list = manualCache.getIfPresent(fileName);
        if(CollectionUtils.isEmpty(list)){
            list = new ArrayList<>();
            String content = getFileContent(fileName);
            String [] array = content.split("\n");
            for (String value : array){
                String [] group = value.split(",");
                for (String gStr : group){
                    char [] c =  gStr.toCharArray();
                    for (char cx : c){
                        list.add(cx+"");
                    }
                }
            }
            manualCache.put(fileName,list);
        }
        return list;
    }

    @Override
    public List<String> parseLastName() throws IOException {
        String fileName = DefaultFileFactory.LAST_NAME;

        List<String> list = manualCache.getIfPresent(fileName);
        if(CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
            String content = getFileContent(fileName);
            String [] array = content.split("\n");
            for (String group : array){
                String arr [] = group.split("、");
                for (String name : arr){
                    list.add(name);
                }
            }

            manualCache.put(fileName,list);
        }
        return list;

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
