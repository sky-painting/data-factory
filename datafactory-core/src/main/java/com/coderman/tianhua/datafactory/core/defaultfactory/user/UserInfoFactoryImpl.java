package com.coderman.tianhua.datafactory.core.defaultfactory.user;

import com.coderman.tianhua.datafactory.core.function.UserFunction;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: UserInfoFactoryImpl <br>
 * date: 2020/12/15 23:15 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
public class UserInfoFactoryImpl implements UserInfoFactory {
    //todo aop check
    /**
     * 默认最大
     */
    private static final int MAX_COUNT = 10000;

    @Override
    public List<String> getHandPhoneList(int num) {
        if(!checkNumber(num)){
            return null;
        }
        List<String> list = new ArrayList<>(MAX_COUNT);
        for (int i = 0;i < num;i++){
            list.add(UserFunction.getOneTel());
        }
        return list;
    }

    @Override
    public List<String> getLandlineNumberList(int num) {
        return null;
    }

    @Override
    public List<String> getEmailList(int num) {
        return null;
    }

    @Override
    public List<String> getPasswordList(int size) {
        return null;
    }

    @Override
    public List<Date> getDateList(int num, String format) {
        return null;
    }

    /**
     * 默认一次生成一万条
     * @param num
     * @return
     */
    private boolean checkNumber(int num){
        if( num <= 0 || num > 10000){
            return false;
        }
        return true;
    }

}
