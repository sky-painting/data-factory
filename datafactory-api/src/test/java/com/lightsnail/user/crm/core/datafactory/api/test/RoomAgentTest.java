package com.lightsnail.user.crm.core.datafactory.api.test;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestFieldRuleVo;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestFieldVo;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestVo;
import com.coderman.utils.response.ResultDataDto;
import org.assertj.core.util.Lists;
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
public class RoomAgentTest {
    @Autowired
    private TestRestTemplate restTemplate;




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


        DataBuildRequestFieldVo dataFactoryRequestFieldVo11 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo11.setFieldTypeStr("String");
        dataFactoryRequestFieldVo11.setFieldName("provinceId");
        dataFactoryRequestFieldVo11.setDefaultValueList(Lists.list("340000000000"));
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
        dataFactoryRequestFieldRuleVo5.setSplitTag(",");
        dataFactoryRequestFieldRuleVo5.setValueCount(3);
        dataFactoryRequestFieldVo12.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo5);


        requestFieldVoList.add(dataFactoryRequestFieldVo12);


        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        long startTime = System.currentTimeMillis();

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/simple",dataFactoryRequestVo, ResultDataDto.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("useTime = "+(endTime - startTime)+"ms");
        System.out.println(JSON.toJSONString(resultDto));

    }

}
