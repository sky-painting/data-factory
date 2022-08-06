package com.tianhua.datafactory.client.function.impl;


import com.tianhua.datafactory.client.function.Function;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * description: UserFunction <br>
 * date: 2020/12/15 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 用户相关的数据工厂工具类生成
 */
@Service
public class EmailFunc implements Function<String> {

    private static SecureRandom random = new SecureRandom();

    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final String[] EMAIL_SUFFIX = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

    @Override
    public String createOneData(String... params) {

        return null;
    }
}
