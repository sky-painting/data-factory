package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;

/**
 * Description:
 * date: 2021/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum ColumnTypeEnums {
    VARCHAR("varchar"),
    TINYINT("tinyint"),
    SMALLINT("smallint"),
    MEDIUMINT("mediumint"),
    BIGINT("bigint"),
    FLOAT("float"),
    DOUBLE("double"),
    DECIMAL("decimal"),
    CHAR("char"),
    TINYBLOD("tinyblob"),
    TINYTEXT("tinytext"),
    BLOB("blob"),
    TEXT("text"),
    MEDIUMBLOB("mediumblob"),
    MEDIUMTEXT("mediumtext"),
    LONGBLOB("longblob"),
    LONGTEXT("longtext"),
    ENUM("enum"),
    SET("set")

    ;
    private String columnType;
    ColumnTypeEnums(String columnType){
        this.columnType = columnType;
    }


    public String getColumnType() {
        return columnType;
    }

    public static Boolean contains(String columnTypeTag){
        for (ColumnTypeEnums columnTypeEnums : ColumnTypeEnums.values()){
            if(columnTypeTag.startsWith(columnTypeEnums.getColumnType())){
                return true;
            }
        }
        return false;
    }

    public static Boolean isInt(String columnTypeTag){
        return columnTypeTag.contains("int");
    }

    public static Boolean isVarchar(String columnTypeTag){
        return columnTypeTag.contains("varchar") || columnTypeTag.contains("text");
    }

    public static Boolean isDate(String columnTypeTag){
        return columnTypeTag.contains("timestamp") || columnTypeTag.contains("date");
    }

    public static Boolean isJson(String columnTypeTag){
        return columnTypeTag.contains("json") || columnTypeTag.contains("JSON");
    }





    /**
     * 数据枚举路由
     * @param enumCode
     * @return
     */
    public static boolean isColumnFieldTypeEnum(String enumCode){
        return "columnTypeEnum".equals(enumCode);
    }

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (ColumnTypeEnums columnFieldTypeEnum : ColumnTypeEnums.values()){
            optionsBO.addOptionItem(columnFieldTypeEnum.getColumnType(), columnFieldTypeEnum.getColumnType()+"");
        }
        return optionsBO;
    }

}
