package com.tianhua.datafactory.domain.enums;


import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    //nacos配置服务
    FROM_NACOS(0,"NACOS"),
    //服务获取
    FROM_SERVICE_API_HTTP(1,"API_HTTP"),

    //服务获取
    FROM_SERVICE_API_RPC(2,"API_RPC"),


    //服务本身自带枚举
    FROM_SERVICE_ENUM(3,"ENUM"),
    //apollo配置服务
    FROM_APOLLO(4,"APOLLO"),

    //函数式数据源，datafactory内置提供
    FUNCTION_DATASOURCE(5,"INNER_FUNCTION"),

    //自定义 ---json文件，excel
    FROM_CUSTOM(6,"自定义数据源"),
    ;
    private int code;
    private String desc;


    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (DataSourceTypeEnum dataSourceTypeEnum : DataSourceTypeEnum.values()){
            optionsBO.addOptionItem(dataSourceTypeEnum.getDesc(), dataSourceTypeEnum.getCode() +"");
        }
        return optionsBO;
    }

    public static String getDesc(Integer enumCode){
       for (DataSourceTypeEnum dataSourceTypeEnum : DataSourceTypeEnum.values()){
           if(dataSourceTypeEnum.getCode() == enumCode.intValue()){
               return dataSourceTypeEnum.getDesc();
           }
       }
       return "未知类型";
    }




    public static boolean isDataSourceType(String enumCode){
        return "dataSourceType".equals(enumCode);
    }

}
