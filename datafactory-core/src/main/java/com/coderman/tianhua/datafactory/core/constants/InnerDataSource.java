package com.coderman.tianhua.datafactory.core.constants;

/**
 * 内建公共数据源-code
 *
 * 数据字典
 */
public interface InnerDataSource {
    /**
     * 身份证--
     * http://www.mamicode.com/info-detail-1832018.html
     */
    String CARD_NUMBER = "com.common.user.cardnumber";

    /**
     * 出生年月日--自建实现
     */
    String BIRTHDAY = "com.common.user.birthday";

    /**
     * 省份名称-code
     */
    String PROVINCE = "com.common.area.province";

    /**
     * 城市名称-code
     */
    String CITY = "com.common.area.city";

    /**
     * 民族-code
     */
    String NATION = "com.common.user.nation";

    /**
     * 姓氏--自建实现
     */
    String LAST_NAME = "com.common.user.lastname.txt";

    /**
     * 名--自建实现
     */
    String FIRST_NAME = "com.common.user.firstname";


}
