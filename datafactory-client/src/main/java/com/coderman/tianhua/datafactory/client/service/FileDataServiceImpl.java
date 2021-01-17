package com.coderman.tianhua.datafactory.client.service;

import com.coderman.tianhua.datafactory.client.enums.FileDataEnums;
import com.coderman.tianhua.datafactory.client.filedata.ParseService;
import com.coderman.tianhua.datafactory.client.utils.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class FileDataServiceImpl implements FileDataService{

    @Override
    public List<String> getFileDataList(String fileName) {

        String beanName = FileDataEnums.getBeanName(fileName);
        if(StringUtils.isEmpty(beanName)){
            //todo throw exception
            return null;
        }
        ParseService parseService = (ParseService)SpringUtil.getBean(beanName);
        return parseService.parseFileData();
    }
}
