package com.coderman.tianhua.datafactory.core.defaultfactory.user;

import java.util.Date;
import java.util.List;

/**
 * description: UserInfoFactory <br>
 * date: 2020/12/15 23:09 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * 用户相关--通用数据工厂
 */
public interface UserInfoFactory {
    /**
     * 生成指定数量的电话号码
     *
     * @param num
     * @return
     */
    List<String> getHandPhoneList(int num);

    /**
     * 生成指定数量的座机号码
     * @param num
     * @return
     */
    List<String> getLandlineNumberList(int num);

    /**
     * 生成指定数量的邮箱
     * @param num
     * @return
     */
    List<String> getEmailList(int num);

    /**
     * 生成指定位数的密码
     * @param size
     * @return
     */
    List<String> getPasswordList(int size);

    /**
     * 生成指定数量的日期yyyy-MM-dd
     * @param num
     * @return
     */
    List<Date> getDateList(int num);



}
