package com.tianhua.datafactory.domain.enums;

/**
 * Description
 * date: 2022/8/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum JavaFieldTypeEnum {
    INTEGER("Integer"),
    LONG("Long"),
    SHORT("Short"),
    STRING("String"),
    DOUBLE("Double"),
    FLOAT("Float"),
    ;

    String type;
    JavaFieldTypeEnum(String type){
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
