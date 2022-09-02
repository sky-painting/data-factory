package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum ReturnTypeEnum {
    /**
     *
     */
    LIST_LONG("LIST_LONG","List<Long>"),
    LIST_INTEGER("LIST_INTEGER","List<Integer>"),
    LIST_STRING("LIST_STRING","List<String>"),
    LIST_OBJECT("LIST_OBJECT","List<Object>"),
    LIST_DTO("LIST_DTO","List<DTO>"),

    SET_LONG("SET_LONG","Set<Long>"),

    VOID("VOID","void"),
    RESULT_DTO("RESULT_DTO","ResultDTO"),

    ;

    /** api类型code **/
    private String type;
    /** api类型描述 **/
    private String desc;


    ReturnTypeEnum(String type,String desc){
        this.type = type;
        this.desc = desc;
    }



    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (ReturnTypeEnum returnTypeEnum : ReturnTypeEnum.values()){
            optionsBO.addOptionItem(returnTypeEnum.getDesc(), returnTypeEnum.getType());
        }
        return optionsBO;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
