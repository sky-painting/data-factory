package com.tianhua.datafactory.client.function.impl;

import com.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.enums.FileDataEnums;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

/**
 * Description
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "chineseNameFunc")
@DataSourceFunction(dataSourceCode = InnerDataSourceCode.CHINESE_NAME)
public class ChineseNameFunc  implements Function<String> {
    @Autowired
    private FileDataService fileDataService;
    private static SecureRandom random = new SecureRandom();

    @Override
    public String createOneData( String... params) {
        List<String> firstNameList = fileDataService.getFileDataList(FileDataEnums.FIRST_NAME.getFileName());
        List<String> lastNameList = fileDataService.getFileDataList(FileDataEnums.LAST_NAME.getFileName());
        return firstNameList.get(random.nextInt(firstNameList.size())) + lastNameList.get(random.nextInt(lastNameList.size()));
    }
}
