package com.tianhua.datafactory;

import com.tianhua.datafactory.core.adapter.HttpApiAdapter;
import com.tianhua.datafactory.domain.bo.HttpApiRequestBO;
import com.tianhua.datafactory.domain.enums.ReturnTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {Application.class})
@Slf4j
public class HttpApiAdaptorTest {
    @Resource(name = "httpApiAdaptorImpl")
    private HttpApiAdapter httpApiAdapter;

    @Test
    public void testHttpGetList(){
        String url = "http://127.0.0.1:8088/getlistobjectdto";

        HttpApiRequestBO httpApiRequestBO = new HttpApiRequestBO(url);
        httpApiRequestBO.setReturnType(ReturnTypeEnum.RESULT_DTO.getType());
        List<String> paramFieldList = new ArrayList<>();
        paramFieldList.add("userName");
        paramFieldList.add("aliasName");
        paramFieldList.add("country");
        paramFieldList.add("age");
        httpApiRequestBO.setParamFieldList(paramFieldList);
        httpApiAdapter.getServiceDataFromHttp(httpApiRequestBO);
    }

}
