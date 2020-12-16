package com.coderman.tianhua.datafactory.core.defaultfactory;

import com.coderman.tianhua.datafactory.core.cache.FileDataCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * description: FileDataParseServiceImpl <br>
 * date: 2020/12/16 23:28 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class FileDataParseServiceImpl  implements FileDataParseService{
    @Autowired
    private FileDataCacheService dataCacheService;

    @Override
    public List<String> parseFirstName() throws IOException {
        String fileName = "firstname";
        String content = dataCacheService.getCacheSource(fileName);
        String [] array = content.split("\n");
        List<String> list = new ArrayList<>();
        for (String value : array){
            String [] group = value.split(",");
            for (String gStr : group){
                char [] c =  gStr.toCharArray();
                for (char cx : c){
                    list.add(cx+"");
                }
            }
        }
        return list;
    }

    @Override
    public List<String> parseLastName() throws IOException {
        String fileName = "firstname";
        String content = dataCacheService.getCacheSource(fileName);
        String [] array = content.split("\n");
        List<String> list = new ArrayList<>();
        for (String group : array){
            String arr [] = group.split("、");
            for (String name : arr){
                list.add(name);
            }
        }
        return list;
    }
}
