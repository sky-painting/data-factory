package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum ReturnWrapClassEnum {
    RESULT_ORIGIN(0,"Origin", "使用原方法的返回值作为返回结果"),
    RESULT_DTO(1,"ResultDTO", "通用接口返回包装类"),
    RESULT_PAGE_DTO(2,"PageDTO", "在通用接口返回包装类的基础上增加PageDTO包装类");

    /**
     * 返回包装类名称
     */
    private Integer returnWrapCode;

    /**
     * 返回包装类
     */
    private String returnWrapClass;

    /**
     * 返回包装类描述
     */
    private String returnWrapDesc;


    ReturnWrapClassEnum(Integer returnWrapCode, String returnWrapClass, String returnWrapDesc){
        this.returnWrapCode = returnWrapCode;
        this.returnWrapClass = returnWrapClass;
        this.returnWrapDesc = returnWrapDesc;
    }

    public static boolean isReturnWrapClass(String enumCode){
        return "returnWrapClass".equals(enumCode);
    }

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (ReturnWrapClassEnum returnWrapClassEnum : ReturnWrapClassEnum.values()){
            optionsBO.addOptionItem(returnWrapClassEnum.getReturnWrapClass(), returnWrapClassEnum.getReturnWrapCode()+"");
        }
        return optionsBO;
    }

    public Integer getReturnWrapCode() {
        return returnWrapCode;
    }

    public String getReturnWrapClass() {
        return returnWrapClass;
    }

    public String getReturnWrapDesc() {
        return returnWrapDesc;
    }


    public static String getDesc(Integer returnWrapCode){
        if(returnWrapCode == null){
            returnWrapCode = 0;
        }
        for (ReturnWrapClassEnum returnWrapClassEnum : ReturnWrapClassEnum.values()){
            if(returnWrapClassEnum.getReturnWrapCode().intValue() == returnWrapCode.intValue()){
                return returnWrapClassEnum.getReturnWrapDesc();
            }
        }
        return "";
    }

    public static  boolean isOrigin(Integer returnWrapCode){
        if(returnWrapCode == null){
            return true;
        }
        return ReturnWrapClassEnum.RESULT_ORIGIN.getReturnWrapCode().intValue() == returnWrapCode.intValue();
    }

    public static  boolean isResultDTO(Integer returnWrapCode){
        if(returnWrapCode == null){
            return false;
        }
        return ReturnWrapClassEnum.RESULT_DTO.getReturnWrapCode().intValue() == returnWrapCode.intValue();
    }

    public static  boolean isResultPageDTO(Integer returnWrapCode){
        if(returnWrapCode == null){
            return false;
        }
        return ReturnWrapClassEnum.RESULT_PAGE_DTO.getReturnWrapCode().intValue() == returnWrapCode.intValue();
    }


}
