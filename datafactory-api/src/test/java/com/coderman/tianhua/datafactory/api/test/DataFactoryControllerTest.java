package com.coderman.tianhua.datafactory.api.test;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.api.vo.DataFactoryRequestFieldRuleVo;
import com.coderman.tianhua.datafactory.api.vo.DataFactoryRequestFieldVo;
import com.coderman.tianhua.datafactory.api.vo.DataFactoryRequestVo;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldRuleBean;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: DataFactoryControllerTest <br>
 * date: 2020/12/7 23:42 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
public class DataFactoryControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 测试基于内置函数的随机数据生成
     */
    @Test
    public void testGenerateFromFunction(){
        DataFactoryRequestVo dataFactoryRequestVo = new DataFactoryRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("学生表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("排课系统");
        dataFactoryRequestVo.setModuleName("学生基本信息模块");
        dataFactoryRequestVo.setServiceName("StudentService");

        List<DataFactoryRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataFactoryRequestFieldVo dataFactoryRequestFieldVo = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataFactoryRequestFieldVo dataFactoryRequestFieldVo2 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataFactoryRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataFactoryRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataFactoryRequestFieldVo dataFactoryRequestFieldVo3 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataFactoryRequestFieldVo dataFactoryRequestFieldVo4 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataFactoryRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataFactoryRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        System.out.println(JSON.toJSONString(resultDto));
    }


    /**
     * 测试基于自定义的随机数据生成
     */
    @Test
    public void testGenerateFromCustomAndFunction(){
        DataFactoryRequestVo dataFactoryRequestVo = new DataFactoryRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("员工表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("HR系统");
        dataFactoryRequestVo.setModuleName("员工信息模块");
        dataFactoryRequestVo.setServiceName("StaffService");

        List<DataFactoryRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataFactoryRequestFieldVo dataFactoryRequestFieldVo = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataFactoryRequestFieldVo dataFactoryRequestFieldVo2 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataFactoryRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataFactoryRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataFactoryRequestFieldVo dataFactoryRequestFieldVo3 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataFactoryRequestFieldVo dataFactoryRequestFieldVo4 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataFactoryRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataFactoryRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        DataFactoryRequestFieldVo dataFactoryRequestFieldVo5 = new DataFactoryRequestFieldVo();
        dataFactoryRequestFieldVo5.setFieldTypeStr("Long");
        dataFactoryRequestFieldVo5.setFieldName("departmentId");
        dataFactoryRequestFieldVo5.setDataSourceCode("com.lightsnail.infosys.department");
        dataFactoryRequestFieldVo5.setFieldName("id");
        requestFieldVoList.add(dataFactoryRequestFieldVo5);


        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        System.out.println(JSON.toJSONString(resultDto));
    }


}
