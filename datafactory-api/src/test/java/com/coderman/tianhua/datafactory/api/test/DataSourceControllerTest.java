package com.coderman.tianhua.datafactory.api.test;

import com.coderman.tianhua.datafactory.api.Application;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
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

import java.util.Date;

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
        dataSourceVO.setUpdateTime(new Date());
        dataSourceVO.setSourceCode("com.coderman.datafactory.demo.DataSourceTypeEnum");
        dataSourceVO.setSourceType(DataSourceTypeEnum.FROM_ENUM.getCode());
        dataSourceVO.setStatus(0);
        dataSourceVO.setTokent("");
        dataSourceVO.setSourceName("数据源类型枚举");
        dataSourceVO.setUrl("");
        dataSourceVO.setVisitStrategy(VisitStrategyEnums.LOCAL_CACHE.getCode());
        dataSourceVO.setProviderSrc("datafactory.DataSourceTypeEnum");
        ResultDto resultDto = restTemplate.postForEntity("/data/source/regist",dataSourceVO, ResultDto.class).getBody();

    }
}
