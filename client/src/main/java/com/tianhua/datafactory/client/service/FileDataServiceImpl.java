package com.tianhua.datafactory.client.service;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.context.FileDataSourceContext;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.filedata.InnerParseService;
import com.tianhua.datafactory.client.utils.SpringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Description:
 * 文件数据服务
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FileDataServiceImpl implements FileDataService{
    private static final String DATA_SOURCE = "datasource";

    @Override
    public List<String> getFileDataList(String fileName) {
        String beanName = FileDataEnums.getBeanName(fileName);
        if(StringUtils.isEmpty(beanName)){
            //todo throw exception
            return null;
        }
        InnerParseService parseService = (InnerParseService)SpringUtil.getBean(beanName);
        return parseService.parseFileData();
    }

    @Override
    public boolean registFileDataSource(FileDataSourceContext fileDataSourceContext) throws IOException {
        // ClassPathResource类的构造方法接收路径名称，自动去classpath路径下找文件
        String jsonPath = DATA_SOURCE+"/"+fileDataSourceContext.getDataSourceCode()+".json";

        ClassPathResource classPathResource = new ClassPathResource(jsonPath);
        classPathResource.getPath();


        File file = classPathResource.getFile();
        if(!file.exists()){
           file.mkdir();
        }
        //将规则信息写入本地resource 目录下
        FileUtils.writeStringToFile(file, JSON.toJSONString(fileDataSourceContext), "utf-8");
        return true;
    }

    @Override
    public boolean loadFileData(String dataSourceCode, Integer loadCount) throws IOException {
        String jsonPath = DATA_SOURCE+"/"+dataSourceCode+".json";
        File file = new File(jsonPath);
        if(!file.exists()){
           return false;
        }
        String jsonStr = FileUtils.readFileToString(new File(jsonPath),"utf-8");
        FileDataSourceContext fileDataSourceContext = JSON.parseObject(jsonStr, FileDataSourceContext.class);


        fileDataSourceContext.getFileUrl();



        return false;
    }

    @Override
    public Object getFileData(String dataSourceCode) {
        return null;
    }
}
