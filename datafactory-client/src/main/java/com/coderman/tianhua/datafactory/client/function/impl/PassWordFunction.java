package com.coderman.tianhua.datafactory.client.function.impl;

import com.coderman.tianhua.datafactory.client.annotations.DataSourceFunction;
import com.coderman.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.coderman.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

/**
 * description: PassWordFunction <br>
 * date: 2021/1/28 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service(value = "passWordFunction")
public class PassWordFunction implements Function<String> {

    private static String reference = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm!@#$%^.,-&*(~)";
    private static Random random = new SecureRandom();
    /**
     * 获取一个密码
     *
     * @param length
     */
    @DataSourceFunction(dataSourceCode = InnerDataSourceCode.PASS_WORD)
    private static String passWord(int length) {
        StringBuffer buffer = new StringBuffer();
        char [] arr = reference.toCharArray();
        for (int i = 0; i < length; i++) {
            buffer.append(arr[random.nextInt(arr.length)]);
        }
        return buffer.toString();
    }

    @Override
    public String createOneData(String methodName, String... params) {
        int length = Integer.parseInt(params[0]);
        return passWord(length);
    }
}
