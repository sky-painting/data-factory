package com.lightsnail.user.crm.core.datafactory.api.test;

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
 * description: RoomLandLoadTest <br>
 * date: 2021/3/28 23:52 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
public class RoomLandLoadTest {
    @Autowired
    private TestRestTemplate restTemplate;


    /**
     * 生成crm.room_agent表数据
     */
    @Test
    public void testGenerateFromALL(){
        DataBuildRequestVo dataFactoryRequestVo = new DataBuildRequestVo();
        dataFactoryRequestVo.setGenerateCount(100);
        dataFactoryRequestVo.setModuleDesc("房东信息表");
        dataFactoryRequestVo.setPersistRequest(0);
        dataFactoryRequestVo.setProjectName("轻蜗牛租房项目");
        dataFactoryRequestVo.setModuleName("crm-房东信息管理模块");
        dataFactoryRequestVo.setServiceName("RoomLandLordService");
        dataFactoryRequestVo.setTableName("room_landlord");
        List<DataBuildRequestFieldVo> requestFieldVoList = new ArrayList<>();


        DataBuildRequestFieldVo chineseNameFieldVO = new DataBuildRequestFieldVo();
        chineseNameFieldVO.setFieldTypeStr("String");
        chineseNameFieldVO.setFieldName("chineseName");
        chineseNameFieldVO.setColumnName("chinese_name");

        chineseNameFieldVO.setDataSourceCode("com.datafactory.user.chineseName");
        DataBuildRequestFieldRuleVo chineseNameFieldRuleVO = new DataBuildRequestFieldRuleVo();
        chineseNameFieldRuleVO.setDepencyFunctionMethod("chineseName");
        chineseNameFieldVO.setDataFactoryRequestFieldRuleVo(chineseNameFieldRuleVO);
        requestFieldVoList.add(chineseNameFieldVO);

        DataBuildRequestFieldVo cardnumberFieldVO = new DataBuildRequestFieldVo();
        cardnumberFieldVO.setDataSourceCode("com.datafactory.user.cardnumber");
        cardnumberFieldVO.setFieldName("cardnumber");
        cardnumberFieldVO.setFieldTypeStr("String");
        cardnumberFieldVO.setColumnName("card_number");
        requestFieldVoList.add(cardnumberFieldVO);


        DataBuildRequestFieldVo dataFactoryRequestFieldVo12 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo12.setFieldTypeStr("String");
        dataFactoryRequestFieldVo12.setFieldName("areaCodes");
        dataFactoryRequestFieldVo12.setColumnName("area_codes");
        dataFactoryRequestFieldVo12.setDataSourceCode("lightsnail-meta-area-core.citybyprovinceId");
        dataFactoryRequestFieldVo12.setDataSourceField("areaId");

        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo5 = new DataBuildRequestFieldRuleVo();
        //设置请求函数

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("provinceId","340000000000");
        dataFactoryRequestFieldRuleVo5.setParameterMap(paramMap);
        dataFactoryRequestFieldRuleVo5.setValueCount(3);
        dataFactoryRequestFieldRuleVo5.setSplitTag(",");
        dataFactoryRequestFieldVo12.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo5);


        //requestFieldVoList.add(dataFactoryRequestFieldVo12);

        DataBuildRequestFieldVo dataFactoryRequestFieldVo2 = new DataBuildRequestFieldVo();
        dataFactoryRequestFieldVo2.setDataSourceCode("com.datafactory.user.telphone");
        dataFactoryRequestFieldVo2.setFieldName("handphone");
        dataFactoryRequestFieldVo2.setColumnName("handphone");
        dataFactoryRequestFieldVo2.setFieldTypeStr("String");
        DataBuildRequestFieldRuleVo dataFactoryRequestFieldRuleVo = new DataBuildRequestFieldRuleVo();
        dataFactoryRequestFieldRuleVo.setDepencyFunctionMethod("tel");
        dataFactoryRequestFieldVo2.setDataFactoryRequestFieldRuleVo(dataFactoryRequestFieldRuleVo);
        requestFieldVoList.add(dataFactoryRequestFieldVo2);




        dataFactoryRequestVo.setDataFactoryRequestFieldVoList(requestFieldVoList);

        long startTime = System.currentTimeMillis();

        ResultDataDto resultDto = restTemplate.postForEntity("/datafactory/generate/sql",dataFactoryRequestVo, ResultDataDto.class).getBody();
        long endTime = System.currentTimeMillis();
        List<String> list = ( List<String>)resultDto.getData();
        for (String str : list){
            System.out.println(str);
        }
    }

}

