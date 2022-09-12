package com.tianhua.datafactory;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.domain.bo.datafactory.ApiMockBO;
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

/**
 * Description
 * 数据工厂构建过程，整体测试内容
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
        dataBuildRequestBO.setBuildCount(100000);
        dataBuildRequestBO.setProjectCode("xxxx");
        dataBuildRequestBO.setParamModelCode("XxxModel");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceCode("com.datafactory.user.chineseName");
        dataBuildRequestFieldBO.setFieldName("chinseName");
        dataBuildRequestFieldBO.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO.setFieldType("String");
        fieldBOList.add(dataBuildRequestFieldBO);


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setDataSourceCode("com.datafactory.user.telphone");
        dataBuildRequestFieldBO2.setFieldName("handPhone");
        dataBuildRequestFieldBO2.setFunction(telPhoneFunc);
        dataBuildRequestFieldBO2.setFieldType("String");
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setDataSourceCode("com.datafactory.user.cardnumber");
        dataBuildRequestFieldBO3.setFieldName("cardNumber");
        dataBuildRequestFieldBO3.setFunction(cardNumberFunction);
        dataBuildRequestFieldBO3.setFieldType("String");
        fieldBOList.add(dataBuildRequestFieldBO3);


        DataBuildRequestFieldBO dataBuildRequestFieldBO4 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO4.setFieldName("userType");
        dataBuildRequestFieldBO4.setFieldType("Integer");
        dataBuildRequestFieldBO4.setDefaultValueList(Lists.newArrayList(1,2,3,4,5));
        fieldBOList.add(dataBuildRequestFieldBO4);

        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("userId");
        dataBuildRequestFieldBO5.setFieldType("Long");
        dataBuildRequestFieldBO5.setDataSourceCode("com.datafactory.user.getRandom");
        dataBuildRequestFieldBO5.setFunction(randomNumFunc);
        fieldBOList.add(dataBuildRequestFieldBO5);






        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            for (int i =0;i< 10;i++){
                long startTime = System.currentTimeMillis();
                result = dataFactoryService.generateData(dataBuildRequestBO);
                long endTime = System.currentTimeMillis();
                log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testDataFactory5(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(10);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setParamModelCode("XxxBO");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();


        DataBuildRequestFieldBO dataBuildRequestFieldBO0 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO0.setDataSourceCode("com.datafactory.user.getRandom");
        dataBuildRequestFieldBO0.setFieldName("id");
        dataBuildRequestFieldBO0.setFunction(randomNumFunc);
        dataBuildRequestFieldBO0.setFieldType("Long");
        String ruleDsl0 = "funcVar=6;";
        dataBuildRequestFieldBO0.setBuildRuleDSL(ruleDsl0);
        fieldBOList.add(dataBuildRequestFieldBO0);


        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO.setDataSourceCode("com.datafactory.user.chineseName");
        dataBuildRequestFieldBO.setFieldName("chineseName");
        dataBuildRequestFieldBO.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO.setFieldType("String");
        String ruleDsl = "prefix=abc;subfix=bdff;";
        dataBuildRequestFieldBO.setBuildRuleDSL(ruleDsl);
        fieldBOList.add(dataBuildRequestFieldBO);


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setFieldName("userIdList");
        dataBuildRequestFieldBO2.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO2.setFieldType("List<Long>");
        String ruleDsl2 = "relyListField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO2.setBuildRuleDSL(ruleDsl2);
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setFieldName("departIdSet");
        dataBuildRequestFieldBO3.setFieldType("Set<Long>");
        String ruleDsl3 = "relySetField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO3.setBuildRuleDSL(ruleDsl3);
        fieldBOList.add(dataBuildRequestFieldBO3);


        DataBuildRequestFieldBO dataBuildRequestFieldBO4 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO4.setFieldName("chineseNameV2");
        dataBuildRequestFieldBO4.setFieldType("String");
        String ruleDsl4 = "relyField=chineseName;";
        dataBuildRequestFieldBO4.setBuildRuleDSL(ruleDsl4);
        fieldBOList.add(dataBuildRequestFieldBO4);


        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("fieldBO");
        dataBuildRequestFieldBO5.setFieldType("FieldBO");
        String ruleDsl5 = "fieldName.relySourceCode=com.datafactory.user.chineseName;paramClassName.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO5.setBuildRuleDSL(ruleDsl5);
        fieldBOList.add(dataBuildRequestFieldBO5);


        DataBuildRequestFieldBO dataBuildRequestFieldBO6 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO6.setFieldName("xxxMapping");
        dataBuildRequestFieldBO6.setFieldType("Map<String,Long>");
        String ruleDsl6 = "relyMapKeyField={a,b,c,d};relyMapValueField={1,2,3,4,5,6,7}";
        dataBuildRequestFieldBO6.setBuildRuleDSL(ruleDsl6);
        fieldBOList.add(dataBuildRequestFieldBO6);


        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateData(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("userIdList = "+JSON.toJSONString(map.get("userIdList")));
                log.info("departIdSet = "+JSON.toJSONString(map.get("departIdSet")));
                log.info("chineseNameV2 = "+JSON.toJSONString(map.get("chineseNameV2")));
                log.info("xxxMapping = "+JSON.toJSONString(map.get("xxxMapping")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testDataFactory6(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(10);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setParamModelCode("XxxBO");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setFieldName("userIdList");
        dataBuildRequestFieldBO2.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO2.setFieldType("List<Long>");
        String ruleDsl2 = "relyListField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO2.setBuildRuleDSL(ruleDsl2);
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setFieldName("departIdSet");
        dataBuildRequestFieldBO3.setFieldType("Set<Long>");
        String ruleDsl3 = "relySetField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO3.setBuildRuleDSL(ruleDsl3);
        fieldBOList.add(dataBuildRequestFieldBO3);


        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("fieldBO");
        dataBuildRequestFieldBO5.setFieldType("List<FieldBO>");
        String ruleDsl5 = "relyCount=10;fieldName.relySourceCode=com.datafactory.user.chineseName;paramClassName.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO5.setBuildRuleDSL(ruleDsl5);
        fieldBOList.add(dataBuildRequestFieldBO5);


        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateData(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("fieldBO = "+JSON.toJSONString(map.get("fieldBO")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDataFactory7(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(10);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setParamModelCode("XxxBO");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();


        DataBuildRequestFieldBO dataBuildRequestFieldBO2 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO2.setFieldName("userIdList");
        dataBuildRequestFieldBO2.setFunction(chineseNameFunc);
        dataBuildRequestFieldBO2.setFieldType("List<Long>");
        String ruleDsl2 = "relyListField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO2.setBuildRuleDSL(ruleDsl2);
        fieldBOList.add(dataBuildRequestFieldBO2);


        DataBuildRequestFieldBO dataBuildRequestFieldBO3 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO3.setFieldName("departIdSet");
        dataBuildRequestFieldBO3.setFieldType("Set<Long>");
        String ruleDsl3 = "relySetField={1,2,3,4,5,6,7,8}";
        dataBuildRequestFieldBO3.setBuildRuleDSL(ruleDsl3);
        fieldBOList.add(dataBuildRequestFieldBO3);


        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("apiBO");
        dataBuildRequestFieldBO5.setFieldType("ApiBO");
        String ruleDsl5 = "relyCount=10;fieldName.relySourceCode=com.datafactory.user.chineseName;paramClassName.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO5.setBuildRuleDSL(ruleDsl5);
        fieldBOList.add(dataBuildRequestFieldBO5);

        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateData(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("apiBO = "+JSON.toJSONString(map.get("apiBO")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testgenerateDataApiRespParam(){
        String apiSign = "sdfasdf:/api/pagelist.get.1";
        try {
            ApiMockBO apiMockBO = new ApiMockBO();
            apiMockBO.setApiSign(apiSign);
            ResultDataDto<List<Map<String, Object>>> resultDataDto = dataFactoryService.generateDataApiRespParam(apiMockBO);
            log.info("resultDataDto = {}",JSON.toJSONString(resultDataDto));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void testDataFactoryMultModel(){
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setBuildCount(1);
        dataBuildRequestBO.setProjectCode("sdfasdf");
        dataBuildRequestBO.setParamModelCode("XxxBO");

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();


        DataBuildRequestFieldBO dataBuildRequestFieldBO5 = new DataBuildRequestFieldBO();
        dataBuildRequestFieldBO5.setFieldName("apiBO");
        dataBuildRequestFieldBO5.setFieldType("ApiBO");
        String ruleDsl5 = "projectCode.relySourceCode=com.datafactory.user.chineseName;apiSign.relySourceCode=com.datafactory.user.getRandom(6)";
        dataBuildRequestFieldBO5.setBuildRuleDSL(ruleDsl5);
        fieldBOList.add(dataBuildRequestFieldBO5);

        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> result = null;
        try {
            long startTime = System.currentTimeMillis();
            result = dataFactoryService.generateData(dataBuildRequestBO);
            long endTime = System.currentTimeMillis();
            log.info("useTime = "+(endTime - startTime)+"ms,size = "+result.getData().size());
            for (Map<String, Object> map : result.getData()){
                log.info("apiBO = "+JSON.toJSONString(map.get("apiBO")));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
