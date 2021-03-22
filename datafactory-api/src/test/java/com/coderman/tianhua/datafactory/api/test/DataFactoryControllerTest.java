package com.coderman.tianhua.datafactory.api.test;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestFieldRuleVo;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestFieldVo;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestVo;
import com.coderman.utils.response.ResultDataDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("学生表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("排课系统");
        dataFactoryRequestVo.setModuleName("学生基本信息模块");
        dataFactoryRequestVo.setServiceName("StudentService");

        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataBuildRequestFieldVo dataFactoryRequestFieldVo = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo3 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo4 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataBuildRequestFieldRuleVo();
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
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("员工表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("HR系统");
        dataFactoryRequestVo.setModuleName("员工信息模块");
        dataFactoryRequestVo.setServiceName("StaffService");

        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataBuildRequestFieldVo dataFactoryRequestFieldVo = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo3 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo4 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo5 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo5.setFieldTypeStr("Long");
        dataFactoryRequestFieldVo5.setFieldName("departmentId");
        dataFactoryRequestFieldVo5.setDataSourceCode("com.lightsnail.infosys.department");
        dataFactoryRequestFieldVo5.setDataSourceField("id");
        requestFieldVoList.add(dataFactoryRequestFieldVo5);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo6 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo6.setFieldTypeStr("int");
        dataFactoryRequestFieldVo6.setFieldName("staffType");
        dataFactoryRequestFieldVo6.setDataSourceCode("com.lightsnail.infosys.staffType");
        dataFactoryRequestFieldVo6.setDataSourceField("code");
        requestFieldVoList.add(dataFactoryRequestFieldVo6);


        //自定义默认值列表
        DataBuildRequestFieldVo dataFactoryRequestFieldVo7 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo7.setFieldTypeStr("int");
        dataFactoryRequestFieldVo7.setFieldName("age");
        //dataFactoryRequestFieldVo7.setDataSourceCode("com.lightsnail.infosys.staffType");
        List<Integer> list = new ArrayList<>();
        list.add(20);
        list.add(22);
        list.add(26);
        list.add(20);
        list.add(29);
        list.add(33);
        list.add(25);


        //随机数模拟薪资
        DataBuildRequestFieldVo dataFactoryRequestFieldVo8 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo8.setFieldTypeStr("Integer");
        dataFactoryRequestFieldVo8.setFieldName("sellary");
        dataFactoryRequestFieldVo8.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo3 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethod("random");
        String [] paramArr = new String[1];
        paramArr[0] = "3";
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo3.setSubfixStr("00");
        dataFactoryRequestFieldVo8.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo3);
        requestFieldVoList.add(dataFactoryRequestFieldVo8);


        //随机数模拟工号
        DataBuildRequestFieldVo dataFactoryRequestFieldVo9 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo9.setFieldTypeStr("String");
        dataFactoryRequestFieldVo9.setFieldName("staffCode");
        dataFactoryRequestFieldVo9.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo4 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethod("random");
        paramArr[0] = "4";
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo4.setPrefixStr("TX");
        dataFactoryRequestFieldVo9.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo4);

        requestFieldVoList.add(dataFactoryRequestFieldVo9);

        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        System.out.println(JSON.toJSONString(resultDto));
    }



    /**
     * 测试基于nacos数据源的数据生成
     */
    @Test
    public void testGenerateFromCustomAndFunctionAndNacos(){
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("员工表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("HR系统");
        dataFactoryRequestVo.setModuleName("员工信息模块");
        dataFactoryRequestVo.setServiceName("StaffService");

        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataBuildRequestFieldVo dataFactoryRequestFieldVo = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo3 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo4 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo5 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo5.setFieldTypeStr("Long");
        dataFactoryRequestFieldVo5.setFieldName("departmentId");
        dataFactoryRequestFieldVo5.setDataSourceCode("com.lightsnail.infosys.department");
        dataFactoryRequestFieldVo5.setDataSourceField("id");
        requestFieldVoList.add(dataFactoryRequestFieldVo5);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo6 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo6.setFieldTypeStr("int");
        dataFactoryRequestFieldVo6.setFieldName("staffType");
        dataFactoryRequestFieldVo6.setDataSourceCode("com.lightsnail.infosys.staffType");
        dataFactoryRequestFieldVo6.setDataSourceField("code");
        requestFieldVoList.add(dataFactoryRequestFieldVo6);


        //自定义默认值列表
        DataBuildRequestFieldVo dataFactoryRequestFieldVo7 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo7.setFieldTypeStr("int");
        dataFactoryRequestFieldVo7.setFieldName("age");
        //dataFactoryRequestFieldVo7.setDataSourceCode("com.lightsnail.infosys.staffType");
        List<Integer> list = new ArrayList<>();
        list.add(20);
        list.add(22);
        list.add(26);
        list.add(20);
        list.add(29);
        list.add(33);
        list.add(25);


        //随机数模拟薪资
        DataBuildRequestFieldVo dataFactoryRequestFieldVo8 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo8.setFieldTypeStr("Integer");
        dataFactoryRequestFieldVo8.setFieldName("sellary");
        dataFactoryRequestFieldVo8.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo3 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethod("random");
        String [] paramArr = new String[1];
        paramArr[0] = "3";
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo3.setSubfixStr("00");
        dataFactoryRequestFieldVo8.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo3);
        requestFieldVoList.add(dataFactoryRequestFieldVo8);


        //随机数模拟工号
        DataBuildRequestFieldVo dataFactoryRequestFieldVo9 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo9.setFieldTypeStr("String");
        dataFactoryRequestFieldVo9.setFieldName("staffCode");
        dataFactoryRequestFieldVo9.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo4 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethod("random");
        paramArr[0] = "4";
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo4.setPrefixStr("TX");
        dataFactoryRequestFieldVo9.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo4);

        requestFieldVoList.add(dataFactoryRequestFieldVo9);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo10 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo10.setFieldTypeStr("int");
        dataFactoryRequestFieldVo10.setFieldName("provinceID");
        dataFactoryRequestFieldVo10.setDataSourceCode("com.lightsnail.app.dict.common.province_group");
        dataFactoryRequestFieldVo10.setDataSourceField("k");
        requestFieldVoList.add(dataFactoryRequestFieldVo10);


        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        long startTime = System.currentTimeMillis();

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("useTime = "+(endTime - startTime)+"ms");
        System.out.println(JSON.toJSONString(resultDto));

    }



       /**
     * 测试基于远程服务的数据生成
     */
    @Test
    public void testGenerateFromALL(){
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("员工表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("HR系统");
        dataFactoryRequestVo.setModuleName("员工信息模块");
        dataFactoryRequestVo.setServiceName("StaffService");

        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataBuildRequestFieldVo dataFactoryRequestFieldVo = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo3 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo4 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo5 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo5.setFieldTypeStr("Long");
        dataFactoryRequestFieldVo5.setFieldName("departmentId");
        dataFactoryRequestFieldVo5.setDataSourceCode("com.lightsnail.infosys.department");
        dataFactoryRequestFieldVo5.setDataSourceField("id");
        requestFieldVoList.add(dataFactoryRequestFieldVo5);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo6 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo6.setFieldTypeStr("int");
        dataFactoryRequestFieldVo6.setFieldName("staffType");
        dataFactoryRequestFieldVo6.setDataSourceCode("com.lightsnail.infosys.staffType");
        dataFactoryRequestFieldVo6.setDataSourceField("code");
        requestFieldVoList.add(dataFactoryRequestFieldVo6);


        //自定义默认值列表
        DataBuildRequestFieldVo dataFactoryRequestFieldVo7 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo7.setFieldTypeStr("int");
        dataFactoryRequestFieldVo7.setFieldName("age");
        //dataFactoryRequestFieldVo7.setDataSourceCode("com.lightsnail.infosys.staffType");
        List<Integer> list = new ArrayList<>();
        list.add(20);
        list.add(22);
        list.add(26);
        list.add(20);
        list.add(29);
        list.add(33);
        list.add(25);


        //随机数模拟薪资
        DataBuildRequestFieldVo dataFactoryRequestFieldVo8 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo8.setFieldTypeStr("Integer");
        dataFactoryRequestFieldVo8.setFieldName("sellary");
        dataFactoryRequestFieldVo8.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo3 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethod("random");
        String [] paramArr = new String[1];
        paramArr[0] = "3";
        dataFactoryRequestFieldRuleVo3.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo3.setSubfixStr("00");
        dataFactoryRequestFieldVo8.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo3);
        requestFieldVoList.add(dataFactoryRequestFieldVo8);


        //随机数模拟工号
        DataBuildRequestFieldVo dataFactoryRequestFieldVo9 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo9.setFieldTypeStr("String");
        dataFactoryRequestFieldVo9.setFieldName("staffCode");
        dataFactoryRequestFieldVo9.setDataSourceCode("com.datafactory.user.getRandom");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo4 = new DataBuildRequestFieldRuleVo();
        //设置请求函数
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethod("random");
        paramArr[0] = "4";
        dataFactoryRequestFieldRuleVo4.setDepencyFunctionMethodParam(paramArr);
        //值带默认后缀
        dataFactoryRequestFieldRuleVo4.setPrefixStr("TX");
        dataFactoryRequestFieldVo9.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo4);

        requestFieldVoList.add(dataFactoryRequestFieldVo9);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo10 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo10.setFieldTypeStr("int");
        dataFactoryRequestFieldVo10.setFieldName("provinceID");
        dataFactoryRequestFieldVo10.setDataSourceCode("com.lightsnail.app.dict.common.province_group");
        dataFactoryRequestFieldVo10.setDataSourceField("k");
        requestFieldVoList.add(dataFactoryRequestFieldVo10);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo11 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo11.setFieldTypeStr("int");
        dataFactoryRequestFieldVo11.setFieldName("cardProvinceID");
        dataFactoryRequestFieldVo11.setDataSourceCode("lightsnail-meta-area-core.province");
        dataFactoryRequestFieldVo11.setDataSourceField("provinceId");
        requestFieldVoList.add(dataFactoryRequestFieldVo11);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo12 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo12.setFieldTypeStr("String");
        dataFactoryRequestFieldVo12.setFieldName("cityID");
        dataFactoryRequestFieldVo12.setDataSourceCode("lightsnail-meta-area-core.citybyprovinceId");
        dataFactoryRequestFieldVo12.setDataSourceField("areaId");

        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo5 = new DataBuildRequestFieldRuleVo();
        //设置请求函数

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("provinceId","340000000000");
        dataFactoryRequestFieldRuleVo5.setParameterMap(paramMap);
        dataFactoryRequestFieldVo12.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo5);


        requestFieldVoList.add(dataFactoryRequestFieldVo12);


        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        long startTime = System.currentTimeMillis();

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("useTime = "+(endTime - startTime)+"ms");
        System.out.println(JSON.toJSONString(resultDto));

    }


    /**
     * 测试生成数据库sql
     */
    @Test
    public void testGenerateToSql(){
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("员工表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("HR系统");
        dataFactoryRequestVo.setModuleName("员工信息模块");
        dataFactoryRequestVo.setServiceName("StaffService");
        dataFactoryRequestVo.setTableName("t_staff");

        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();
        DataBuildRequestFieldVo dataFactoryRequestFieldVo = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo.setDataSourceCode("com.datafactory.user.cardnumber");
        dataFactoryRequestFieldVo.setFieldName("cardnumber");
        dataFactoryRequestFieldVo.setFieldTypeStr("String");
        dataFactoryRequestFieldVo.setColumnName("card_number");
        requestFieldVoList.add(dataFactoryRequestFieldVo);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("telphone");
        dataFactoryRequestFieldVo2.setColumnName("telphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo3 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo3.setFieldTypeStr("String");
        dataFactoryRequestFieldVo3.setColumnName("bank_card_number");
        dataFactoryRequestFieldVo3.setFieldName("bankCardNumber");
        dataFactoryRequestFieldVo3.setDataSourceCode("com.datafactory.bank.cardNumber");

        requestFieldVoList.add(dataFactoryRequestFieldVo3);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo4 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo4.setFieldTypeStr("String");
        dataFactoryRequestFieldVo4.setFieldName("chineseName");
        dataFactoryRequestFieldVo4.setColumnName("chinese_name");
        dataFactoryRequestFieldVo4.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo2 = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo2.setDepencyFunctionMethod("chineseName");
        dataFactoryRequestFieldVo4.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo2);

        requestFieldVoList.add(dataFactoryRequestFieldVo4);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo5 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo5.setFieldTypeStr("Long");
        dataFactoryRequestFieldVo5.setFieldName("departmentId");
        dataFactoryRequestFieldVo5.setColumnName("department_id");
        dataFactoryRequestFieldVo5.setDataSourceCode("com.lightsnail.infosys.department");
        dataFactoryRequestFieldVo5.setDataSourceField("id");
        requestFieldVoList.add(dataFactoryRequestFieldVo5);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo6 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo6.setFieldTypeStr("int");
        dataFactoryRequestFieldVo6.setFieldName("staffType");
        dataFactoryRequestFieldVo6.setColumnName("staff_type");
        dataFactoryRequestFieldVo6.setDataSourceCode("com.lightsnail.infosys.staffType");
        dataFactoryRequestFieldVo6.setDataSourceField("code");
        requestFieldVoList.add(dataFactoryRequestFieldVo6);


        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        long startTime = System.currentTimeMillis();

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/sql",dataFactoryRequestVo, ResultDataDto.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("useTime = "+(endTime - startTime)+"ms");
        System.out.println(JSON.toJSONString(resultDto));

    }

}
