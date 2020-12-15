package com.coderman.tianhua.datafactory.core.defaultfactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * description: FileDataService <br>
 * date: 2020/12/15 23:50 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class FileDataService {
    private static final String FACTORY_PATH = "defaultfactory";

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
