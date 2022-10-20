package com.tianhua.datafactory.client.filedata;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Description:
 * date: 2021/1/15
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FileReadService {
    private static final String FACTORY_PATH = "defaultfactory";

    /**
     * 获取数据工厂文件内容
     * @param fileName
     * @return
     * @throws IOException
     */
    public String getFileContent(String fileName) {
        // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
        String path = FACTORY_PATH+"/"+fileName;
        ClassPathResource classPathResource = new ClassPathResource(path);

        // 获得File对象，当然也可以获取输入流对象
        File file = null;
        try {
            file = classPathResource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public File getDataFile(String fileName) {
        // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
        String path = FACTORY_PATH+"/"+fileName;
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            return classPathResource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
