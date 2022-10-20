package com.tianhua.datafactory.client.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */

public class ReturnWrapClassContext {



    /**
     * 返回包装类型
     */
    private String returnWrapClass;

    /**
     * 包装类中的属性模型
     */
    private Map<String,String> classFieldMap = new HashMap<>();

    /**
     * 承载数据的属性名
     */
    private String dataField;


    /**
     * 包装类中的部分属性的默认值
     */
    private Map<String, List<Object>> fieldDefaultValueMap = new HashMap<>();


    public ReturnWrapClassContext(String returnWrapClass, String dataField){
        this.returnWrapClass = returnWrapClass;
        this.dataField = dataField;
    }


    public String getReturnWrapClass() {
        return returnWrapClass;
    }

    public void setReturnWrapClass(String returnWrapClass) {
        this.returnWrapClass = returnWrapClass;
    }

    public Map<String, String> getClassFieldMap() {
        return classFieldMap;
    }

    public void setClassFieldMap(Map<String, String> classFieldMap) {
        this.classFieldMap = classFieldMap;
    }

    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public Map<String, List<Object>> getFieldDefaultValueMap() {
        return fieldDefaultValueMap;
    }

    public void setFieldDefaultValueMap(Map<String, List<Object>> fieldDefaultValueMap) {
        this.fieldDefaultValueMap = fieldDefaultValueMap;
    }

    /**
     * 增加属性
     * @param fieldName
     * @param fieldType
     */
    public void addField(String fieldName, String fieldType){
        classFieldMap.put(fieldName, fieldType);
    }


    /**
     * 增加属性默认值列表
     * @param fieldName
     * @param defaultValueList
     */
    public void addFieldDefaultValueList(String fieldName, List<Object> defaultValueList){
        fieldDefaultValueMap.put(fieldName, defaultValueList);
    }

}
