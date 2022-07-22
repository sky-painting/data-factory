package com.tianhua.datafactory.domain.support.kvpair;

import java.util.Objects;

/**
 * Description:
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum KVTypeEnum {
    //默认k-v都是string
    STRING(0,"string字符串类型"),
    INTEGER(1,"integer整数类型"),
    LONG(2,"Long整数类型"),
    JSON_OBJECT(3,"对象json str类型-value_json使用"),

    ;
    private Integer type;
    private String desc;
    KVTypeEnum(Integer type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean isInteger(Integer integer){
        return Objects.equals(KVTypeEnum.INTEGER.type,integer);
    }

    public static boolean isLong(Integer lon){
        return Objects.equals(KVTypeEnum.LONG.type,lon);
    }

    public static boolean isString(Integer str){
        return Objects.equals(KVTypeEnum.STRING.type,str);
    }


    public static boolean isJSONObject(Integer obj){
        return Objects.equals(KVTypeEnum.JSON_OBJECT.type,obj);
    }
}
