package com.tianhua.datafactory.domain.enums;

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
    INT("int"),
    DATE("date"),
    TEXT("text"),
    TIMESTAMP("timestamp"),
    BIGINT("bigint"),
    TINYINT("tinyint"),

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
}
