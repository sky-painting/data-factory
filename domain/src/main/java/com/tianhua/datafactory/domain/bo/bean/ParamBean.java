package com.tianhua.datafactory.domain.bo.bean;

/**
 * Description:
 * date: 2022/3/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ParamBean {


    public ParamBean(){}
    public ParamBean(String paramClass, String paramName){
        this.paramClass = paramClass;
        this.paramName = paramName;
    }

    /**
     * 参数类型
     */
    private String paramClass;

    /**
     * 参数类型
     */
    private String paramName;


    /**
     * 是否是范型参数
     */
    private boolean genericType;


    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamClass() {
        return paramClass;
    }

    public void setParamClass(String paramClass) {
        this.paramClass = paramClass;
    }


    public boolean isGenericType() {
        return genericType;
    }

    public void setGenericType(boolean genericType) {
        this.genericType = genericType;
    }

    public static  ParamBean getInstance(String paramClass, String paramName, Boolean genericType){
        ParamBean paramBean = new ParamBean(paramClass,paramName);
        paramBean.setGenericType(genericType);
        return paramBean;
    }
}
