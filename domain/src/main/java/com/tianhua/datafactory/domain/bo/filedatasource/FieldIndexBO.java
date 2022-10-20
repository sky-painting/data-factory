package com.tianhua.datafactory.domain.bo.filedatasource;

import lombok.Data;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class FieldIndexBO {
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
}
