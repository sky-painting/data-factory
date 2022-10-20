package com.tianhua.datafactory.domain.ability;

import com.tianhua.datafactory.domain.GlobalConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Description: 读取plantUML类图统一处理类
 * date: 2021/10/25
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ReadPlantUMLDocService {

    @Autowired
    private ResourceLoader resourceLoader;





    /**
     * 读取plantUMl文件通用方法
     * @param plantUMLFileDir
     * @param applicationName
     * @param plantUMLFileName
     * @return
     */
    private List<String> readPlantUMLContent(String plantUMLFileDir,String applicationName,String plantUMLFileName){
        if(StringUtils.isEmpty(plantUMLFileName)){
            System.out.println("plantUMLFileName is empty,can't read content----------------!!!!!!!!!!");
            return null;
        }
        try {
            Resource resource = resourceLoader.getResource("classpath:"+plantUMLFileDir+"/"+applicationName+"/"+plantUMLFileName);
            File file = resource.getFile();
            //File file = ResourceUtils.getFile("classpath:"+plantUMLFileDir+"/"+applicationName+"/"+plantUMLFileName);
            return FileUtils.readLines(file,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
