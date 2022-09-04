package com.tianhua.datafactory.domain.enums;


import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    UN_KNOWN(-1,"UN_KNOWN"),

    //服务获取
    FROM_SERVICE_API_HTTP(1,"API_HTTP"),

    //服务获取
    FROM_DUBBO(2,"DUBBO_RPC"),

    //服务本身自带枚举-->plantUMl解析到KV表中
    FROM_SERVICE_ENUM(3,"ENUM"),

    //来自文件数据源的数据
    FROM_FILE_DATA(4,"fileData"),

    //函数式数据源，datafactory内置提供
    FUNCTION_DATASOURCE(5,"INNER_FUNCTION"),



    /**
     * 属性默认值
     */
    FIELD_DEFAULT(6,"DEFAULT"),
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
