package com.tianhua.datafactory.domain;


/**
 * Description:
 * date: 2021/7/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class GlobalConstant {
    /**
     * 以!开头的则忽略解析
     */
    public static final String PLANT_DOC_IGNORE = "!";

    /**
     * plantUML的API类描述
     */
    public static final String API = "api";


    public static final String DTO = "dto";

    public static final String VO = "vo";

    public static final String ENUM = "enum";

    /**
     * 存放api文档的地方
     */
    public static final String PLANT_UML_FILE_DIR = "webapp-model";
    /**
     * 主键ID
     */
    public static final String PKEY = "pkey";
    /**
     * 是否是联合索引
     */
    public static final String UKEY = "ukey";

    /**
     * 是否是普通索引
     */
    public static final String KEY = "key";

}

