package com.tianhua.datafactory.client.constants;

/**
 * 内建公共数据源-code
 * <p>
 * 数据字典--内置实现
 * com.datafactory开头
 */
public class InnerDataSourceCode {

    /**
     * 默认的内置前缀
     */
    public static final String DEFAULT_PREFIX = "com.datafactory";


    /**
     * 身份证--内置实现
     * http://www.mamicode.com/info-detail-1832018.html
     */
    public static final String CARD_NUMBER = "com.datafactory.user.cardnumber";

    /**
     * 日期--内置实现 yyyy-MM-dd
     */
    public static final String DATE = "com.datafactory.date.date";


    /**
     *  时间--内置实现 yyyy-MM-dd HH:mm:SS
     */
    public static final String DATA_TIME = "com.datafactory.date.datetime";
    /**
     * 姓氏--内置实现
     */
    public static final String LAST_NAME = "com.datafactory.user.lastname";

    /**
     * 名--内置实现
     */
    public static final String FIRST_NAME = "com.datafactory.user.firstname";

    /**
     * 邮箱--内置实现
     */
    public static final String EMAIL_NAME = "com.datafactory.user.email";

    /**
     * 密码--内置实现
     */
    public static final String PASS_WORD = "com.datafactory.user.password";

    /**
     * 姓名--内置实现
     */
    public static final String CHINESE_NAME = "com.datafactory.user.chineseName";


    /**
     * 评论内容--内置实现
     */
    public static final String COMMENT = "com.datafactory.user.comment";



    /**
     * 电话号码--内置实现
     */
    public static final String TEL_PHONE = "com.datafactory.user.telphone";

    /**
     * 银行卡号码--内置实现
     */
    public static final String BANK_CARD = "com.datafactory.bank.cardNumber";

    /**
     * 汉字转拼音实现--内置实现
     */
    public static final String TO_PIN_YIN = "com.datafactory.user.toPinYin";



    /**
     * 提取每个汉字的首字母--内置实现
     */
    public static final String TO_PIN_YIN_HEAD = "com.datafactory.user.toPinYinHead";


    /**
     * 获取一个随机整数---内置实现
     */
    public static final String RANDOM = "com.datafactory.user.getRandom";


    /**
     * 获取一个随机浮点数数---内置实现
     */
    public static final String RANDOM_FLOAT = "com.datafactory.random.float";



    /**
     * 一个英文名单词
     */
    public static final String ONE_ENGLISH_WORD = "com.datafactory.user.oneEnWord";


    /**
     * 时间戳 当前时间戳
     */
    public static final String CURRENT_TIME = "com.datafactory.currenttime";



    /**
     * 获取一个随机数---内置实现
     */
    public static final String SNOWFLAKE_ID = "com.datafactory.user.snowflakeid";


}
