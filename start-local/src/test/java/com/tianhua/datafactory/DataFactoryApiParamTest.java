package com.tianhua.datafactory;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.domain.bo.datafactory.ApiMockBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/8/29
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class DataFactoryApiParamTest {
    @Resource(name = "cardNumberFunction")
    private Function cardNumberFunction;

    @Resource(name = "telPhoneFunc")
    private Function telPhoneFunc;

    @Resource(name = "chineseNameFunc")
    private Function chineseNameFunc;

    @Resource(name = "randomNumFunc")
    private Function randomNumFunc;

    @Autowired
    private DataFactoryService dataFactoryService;

    @Test
    public void testDataFactoryApiRequestParam(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(10);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setApiSign("");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();



        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setFieldName("apiBO");
        dataBuildRequestFieldBO.setFieldType("ApiBO");
        String ruleDsl = "apiSign.relySourceCode=com.datafactory.user.chineseName;projectCode.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO.setBuildRuleDSL(ruleDsl);
        fieldBOList.add(dataBuildRequestFieldBO);


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setFieldName("validate");
        dataBuildRequestFieldBO2.setFieldType("Boolean");
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        dataBuildRequestFieldBO2.setDefaultValueList(list);
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setFieldName("projectCode");
        dataBuildRequestFieldBO3.setFieldType("String");
        dataBuildRequestFieldBO3.setDataSourceCode("com.datafactory.user.chineseName");
        fieldBOList.add(dataBuildRequestFieldBO3);



        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateDataApiReqParam(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("apiBO = "+ JSON.toJSONString(map.get("apiBO")));
                log.info("validate = "+ JSON.toJSONString(map.get("validate")));
                log.info("projectCode = "+ JSON.toJSONString(map.get("projectCode")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testDataFactoryApiResponseParam(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(10);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setApiSign("");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();



        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setFieldName("apiBO");
        dataBuildRequestFieldBO.setFieldType("ApiBO");
        String ruleDsl = "apiSign.relySourceCode=com.datafactory.user.chineseName;projectCode.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO.setBuildRuleDSL(ruleDsl);
        fieldBOList.add(dataBuildRequestFieldBO);


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setFieldName("validate");
        dataBuildRequestFieldBO2.setFieldType("Boolean");
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        dataBuildRequestFieldBO2.setDefaultValueList(list);
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setFieldName("projectCode");
        dataBuildRequestFieldBO3.setFieldType("String");
        dataBuildRequestFieldBO3.setDataSourceCode("com.datafactory.user.chineseName");
        fieldBOList.add(dataBuildRequestFieldBO3);



        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            String apiSign = "";
            ApiMockBO apiMockBO = new ApiMockBO();
            result = dataFactoryService.generateDataApiRespParam(apiMockBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("apiBO = "+ JSON.toJSONString(map.get("apiBO")));
                log.info("validate = "+ JSON.toJSONString(map.get("validate")));
                log.info("projectCode = "+ JSON.toJSONString(map.get("projectCode")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
