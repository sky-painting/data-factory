package com.coderman.tianhua.datafactory.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisitStrategyEnums {
    /**
     * 这里指数据工厂平台会通过定时任务获取数据源更新到数据工厂平台本身的数据库中
     */
    LOCAL_CACHE(1,"本地缓存"),
    /**
     * 这里则每次使用都会通过url的方式获取最新数据
     *
     */
    DYNAMIC_ACCESS(0,"动态获取"),
    ;
    private int code;
    private String desc;

}
