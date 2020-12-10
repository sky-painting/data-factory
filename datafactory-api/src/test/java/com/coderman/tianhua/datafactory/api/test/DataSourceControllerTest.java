package com.coderman.tianhua.datafactory.api.test;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: DataSourceControllerTest <br>
 * date: 2020/12/8 23:10 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
public class DataSourceControllerTest {
    protected Logger logger = LoggerFactory.getLogger(DataSourceControllerTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRegistDataSource(){
        DataSourceVO dataSourceVO = new DataSourceVO();
        dataSourceVO.setCreateTime(new Date());
        dataSourceVO.setCreateUserId(1L);
        dataSourceVO.setUpdateUserId(1L);
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_NACOS.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setToken("");
        dataSourceVO.setSourceName("数据源类型枚举");
        dataSourceVO.setUrl("");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.LOCAL_CACHE.getCode());
        dataSourceVO.setProviderSrc("nacos");

        dataSourceVO.setDataId("com.lightsnail.snail.room");
        dataSourceVO.setGroupId("room_source_type");

        List<KVPair<Integer,String>> kvPairList = new ArrayList<>();
        for(DataSourceTypeEnum dataSourceTypeEnum : DataSourceTypeEnum.values()){
            kvPairList.add(KVPair.build(dataSourceTypeEnum.getCode(),dataSourceTypeEnum.getDesc()));
        }
        dataSourceVO.setDataContentJson(JSON.toJSONString(kvPairList));
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();

    }


    @Test
    public void testGetById(){
        ResultDataDto resultDto = restTemplate.getForEntity("/data/source/get?id=6", ResultDataDto.class).getBody();
        System.out.println(JSON.toJSONString(resultDto));
    }
}
