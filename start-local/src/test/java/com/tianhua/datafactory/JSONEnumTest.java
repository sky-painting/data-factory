package com.tianhua.datafactory;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.demomodel.UserInfo;
import com.tianhua.datafactory.domain.enums.ReturnTypeEnum;

/**
 * Description
 * date: 2022/9/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class JSONEnumTest {
    public static void main(String[] args) {
        UserInfo userInfo = UserInfo.builder()
                .id(1L).company("ssdsdsd")
                .city("sdsd").age(12)
                .userName("sdfsa").returnTypeEnum(ReturnTypeEnum.LIST_DTO).build();
        System.out.println(JSON.toJSONString(userInfo));

        String x = "{\"age\":12,\"city\":\"sdsd\",\"company\":\"ssdsdsd\",\"id\":1,\"returnTypeEnum\":\"LIST_DTO\",\"userName\":\"sdfsa\"}";

        UserInfo userInfo2 = JSON.parseObject(x,UserInfo.class);
        System.out.println("type = "+userInfo2.getReturnTypeEnum().getType());

    }
}
