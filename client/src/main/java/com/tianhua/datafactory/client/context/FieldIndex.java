package com.tianhua.datafactory.client.context;

import java.util.Locale;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */

public class FieldIndex {
    /**
     * 属性对应的列的索引编号
     */
    private Integer index;

    /**
     * 属性名称
     */
    private String fieldName;

    /**
     * 属性类型
     */
    private String fieldType;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isLong(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("long");
    }

    public boolean isInteger(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("integer") || this.fieldType.toLowerCase(Locale.ROOT).equals("int") ;
    }

    public boolean isShort(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("short") || this.fieldType.toLowerCase(Locale.ROOT).equals("short") ;
    }

    public boolean isString(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("string");
    }


    public boolean isDouble(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("double");
    }


    public boolean isDate(){
        return this.fieldType.toLowerCase(Locale.ROOT).equals("date");
    }

}
