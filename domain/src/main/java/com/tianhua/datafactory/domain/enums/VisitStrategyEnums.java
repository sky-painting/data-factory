package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisitStrategyEnums {

    /**
     * 内置数据源或者导入的数据源文件，json,excel等
     */
    LOCAL_FILE(2,"本地文件"),
    /**
     * 内置数据源或者导入的数据源文件，json,excel等
     */
    INNER_FUNCTION(3,"内置函数"),

    /**
     * 这里指数据工厂平台会通过定时任务获取数据源更新到数据工厂平台本身的数据库中
     */
    LOCAL_CACHE(1,"本地缓存"),
    /**
     * 这里则每次使用都会通过url的方式获取最新数据
     *
     */
    DYNAMIC_ACCESS(0,"远程动态获取"),
    ;
    private int code;
    private String desc;



    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (VisitStrategyEnums visitStrategyEnums : VisitStrategyEnums.values()){
            optionsBO.addOptionItem(visitStrategyEnums.getDesc(), visitStrategyEnums.getCode() +"");
        }
        return optionsBO;
    }

    public static boolean isVisitStrategy(String enumCode){
        return "visiteStrategy".equals(enumCode);
    }

    public static String getDesc(Integer visit){
        for (VisitStrategyEnums visitStrategyEnums : VisitStrategyEnums.values()){
            if(visitStrategyEnums.getCode() == visit.intValue()){
                return visitStrategyEnums.getDesc();
            }
        }
        return "未知类型";
    }




}
