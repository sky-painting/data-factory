package com.tianhua.datafactory;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Resource(name = "chineseNameFunc")
    private Function chineseNameFunc;

    @Resource(name = "randomNumFunc")
    private Function randomNumFunc;

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
    @Test
    public void testDataFactory2(){
        String dataSourceCode = "com.lightsnail.infosys.department#departName";
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceType(2);
        dataBuildRequestFieldBO.setDataSourceCode(dataSourceCode);
        String result = dataFactoryService.buildData(dataBuildRequestFieldBO);
        log.info("result = "+result);
    }


    @Test
    public void testDataFactoryx(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(100);
        dataBuildRequestBO.setProjectCode("xxxx");
        dataBuildRequestBO.setParamModelCode("XxxModel");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceCode("com.datafactory.user.chineseName");
        dataBuildRequestFieldBO.setFieldName("chinseName");
        dataBuildRequestFieldBO.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO.setFieldTypeStr("String");
        fieldBOList.add(dataBuildRequestFieldBO);


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setDataSourceCode("com.datafactory.user.telphone");
        dataBuildRequestFieldBO2.setFieldName("handPhone");
        dataBuildRequestFieldBO2.setFunction(telPhoneFunc);
        dataBuildRequestFieldBO2.setFieldTypeStr("String");
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setDataSourceCode("com.datafactory.user.cardnumber");
        dataBuildRequestFieldBO3.setFieldName("cardNumber");
        dataBuildRequestFieldBO3.setFunction(cardNumberFunction);
        dataBuildRequestFieldBO3.setFieldTypeStr("String");
        fieldBOList.add(dataBuildRequestFieldBO3);


        DataBuildRequestFieldBO dataBuildRequestFieldBO4 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO4.setFieldName("userType");
        dataBuildRequestFieldBO4.setFieldTypeStr("Integer");
        dataBuildRequestFieldBO4.setDefaultValueList(Lists.newArrayList(1,2,3,4,5));
        fieldBOList.add(dataBuildRequestFieldBO4);

        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("userId");
        dataBuildRequestFieldBO5.setFieldTypeStr("Long");
        dataBuildRequestFieldBO5.setDataSourceCode("com.datafactory.user.getRandom");
        dataBuildRequestFieldBO5.setFunction(randomNumFunc);
        fieldBOList.add(dataBuildRequestFieldBO5);


        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateData(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            result.getData().stream().forEach(map-> System.out.println(JSON.toJSONString(map)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
