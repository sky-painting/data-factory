package com.coderman.tianhua.datafactory.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    //动态获取策略
    FROM_NACOS(0,"NACOS"),
    //看情况
    FROM_SERVICE_API(1,"API"),
    //枚举则保存在数据库中
    FROM_ENUM(2,"ENUM"),
    //枚举则保存在数据库中
    FROM_CUSTOM(3,"自定义"),
    ;
    private int code;
    private String desc;

}
