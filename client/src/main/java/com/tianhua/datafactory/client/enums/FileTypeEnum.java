package com.tianhua.datafactory.client.enums;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum FileTypeEnum {
    TXT("txt"),
    JSON("json"),
    EXCEL("excel"),

    ;
    String type;
    FileTypeEnum(String fileType){
        this.type = fileType;
    }

    public String getType() {
        return type;
    }
}
