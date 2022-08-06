package com.tianhua.datafactory;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Description
 * date: 2022/8/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class DataFactoryTest {
    @Autowired
    private DataFactoryService dataFactoryService;

    @Resource(name = "cardNumberFunction")
    private Function cardNumberFunction;

    @Resource(name = "telPhoneFunc")
    private Function telPhoneFunc;

    @Test
    public void testDataFactory3(){
        String dataSourceCode = "sdfasdf:ModelTypeEnum#type";
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceType(3);
        dataBuildRequestFieldBO.setDataSourceCode(dataSourceCode);
        String result = dataFactoryService.buildData(dataBuildRequestFieldBO);
        log.info("result = "+result);
    }
    @Test
    public void testDataFactory4(){
        String dataSourceCode = "com.datafactory.user.cardnumber";
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceType(4);
        dataBuildRequestFieldBO.setDataSourceCode(dataSourceCode);
        dataBuildRequestFieldBO.setFunction(cardNumberFunction);
        String result = dataFactoryService.buildData(dataBuildRequestFieldBO);
        log.info("result = "+result);
    }


    @Test
    public void testDataFactory42(){
        String dataSourceCode = "com.datafactory.user.telphone";
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceType(4);
        dataBuildRequestFieldBO.setDataSourceCode(dataSourceCode);
        dataBuildRequestFieldBO.setFunction(telPhoneFunc);
        String result = dataFactoryService.buildData(dataBuildRequestFieldBO);
        log.info("result = "+result);
    }


}
