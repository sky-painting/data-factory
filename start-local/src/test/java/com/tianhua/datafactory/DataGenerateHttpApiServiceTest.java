package com.tianhua.datafactory;

import com.tianhua.datafactory.core.service.DataGenerateService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.domain.enums.ReturnTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class DataGenerateHttpApiServiceTest {

    @Resource(name = "dataGenerateHttpApiServiceImpl")
    private DataGenerateService dataGenerateService;


    @Test
    public void testGet(){

        DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO<>();
        dataBuildRequestFieldBO.setFieldName("userName");
        dataBuildRequestFieldBO.setFieldType("String");
        dataBuildRequestFieldBO.setDataSourceCode("userService:com.snail.user.baseinfo");

        DataSourceBO dataSourceBO = new DataSourceBO();
        dataSourceBO.setSourceCode("userService:com.snail.user.baseinfo");
        dataSourceBO.setProviderDomainUrl("http://127.0.0.1:8088");
        dataSourceBO.setUrl("/getlistobjectdtoparam");
        dataSourceBO.setProviderService("userService");
        dataSourceBO.setStructType(ReturnTypeEnum.RESULT_DTO.getType());

        List<DataSourceRespConfigBO> dataSourceRespConfigList = new ArrayList<>();
        dataSourceRespConfigList.add(new DataSourceRespConfigBO("userName","String"));
        dataSourceRespConfigList.add(new DataSourceRespConfigBO("country","String"));
        dataSourceRespConfigList.add(new DataSourceRespConfigBO("age","Integer"));
        dataSourceRespConfigList.add(new DataSourceRespConfigBO("aliasName","String"));

        dataSourceBO.setDataSourceRespConfigList(dataSourceRespConfigList);


        List<DataSourceReqConfigBO> dataSourceReqConfigBOList = new ArrayList<>();
        dataSourceReqConfigBOList.add(new DataSourceReqConfigBO("page","2"));
        dataSourceReqConfigBOList.add(new DataSourceReqConfigBO("pageSize","100"));

        dataSourceBO.setDataSourceReqConfigList(dataSourceReqConfigBOList);

        dataBuildRequestFieldBO.setDataSourceBO(dataSourceBO);
        dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);

        dataGenerateService.getRandomData(dataSourceFieldRequestBean);

    }

}
