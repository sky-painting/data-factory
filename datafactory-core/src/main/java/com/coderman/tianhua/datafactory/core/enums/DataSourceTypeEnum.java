package com.coderman.tianhua.datafactory.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    FROM_NACOS(0,"NACOS"),
    FROM_SERVICE_API(1,"API"),
    FROM_ENUM(2,"ENUM"),
    FROM_CUSTOM(3,"自定义"),
    ;
    private int code;
    private String desc;

}
