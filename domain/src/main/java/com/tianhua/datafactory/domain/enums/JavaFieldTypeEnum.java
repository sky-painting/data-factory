package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * Description
 * date: 2022/8/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum JavaFieldTypeEnum {
    INTEGER("Integer"),
    INT("int"),
    LONG("Long"),
    LONG_BASIC("long"),

    SHORT("Short"),
    SHORT_BASIC("short"),
    STRING("String"),
    DOUBLE("Double"),
    DOUBLE_BASIC("double"),
    FLOAT("Float"),
    FLOAT_BASIC("float"),
    DATE("Date"),
    DATE_TIME("DateTime"),

    LOCALDATE ("LocalDate"),

    OBJECT ("Object"),

    ;

    String type;
    JavaFieldTypeEnum(String type){
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public static boolean isInt(String type){
        return StringUtils.isNotEmpty(type) && (JavaFieldTypeEnum.INTEGER.getType().equals(type) || JavaFieldTypeEnum.INT.getType().equals(type));
    }

    public static boolean isLong(String type){
        return StringUtils.isNotEmpty(type) &&  (JavaFieldTypeEnum.LONG.getType().equals(type) || JavaFieldTypeEnum.LONG_BASIC.getType().equals(type));
    }

    public static boolean isShort(String type){
        return StringUtils.isNotEmpty(type) &&  (JavaFieldTypeEnum.SHORT.getType().equals(type) || JavaFieldTypeEnum.SHORT_BASIC.getType().equals(type));
    }

    public static boolean isDouble(String type){
        return StringUtils.isNotEmpty(type) &&  (JavaFieldTypeEnum.DOUBLE.getType().equals(type) || JavaFieldTypeEnum.DOUBLE_BASIC.getType().equals(type));
    }

    public static boolean isFloat(String type){
        return  StringUtils.isNotEmpty(type) &&  (JavaFieldTypeEnum.FLOAT.getType().equals(type) || JavaFieldTypeEnum.FLOAT_BASIC.getType().equals(type));
    }

    public static boolean isString(String type){
        return   StringUtils.isNotEmpty(type) && JavaFieldTypeEnum.STRING.getType().toLowerCase().equals(type.toLowerCase());
    }

    public static boolean isDate(String type){
        return   StringUtils.isNotEmpty(type) && JavaFieldTypeEnum.DATE.getType().toLowerCase().equals(type.toLowerCase());
    }


    public static boolean isDateTime(String type){
        return   StringUtils.isNotEmpty(type) && JavaFieldTypeEnum.DATE_TIME.getType().toLowerCase().equals(type.toLowerCase());
    }



    public static boolean isLocalDate(String type){
        return   StringUtils.isNotEmpty(type) && JavaFieldTypeEnum.LOCALDATE.getType().toLowerCase().equals(type.toLowerCase());
    }


    /**
     * 数据枚举路由
     * @param enumCode
     * @return
     */
    public static boolean isJavaFieldTypeEnum(String enumCode){
        return "javaFieldType".equals(enumCode);
    }

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (JavaFieldTypeEnum javaFieldTypeEnum : JavaFieldTypeEnum.values()){
            optionsBO.addOptionItem(javaFieldTypeEnum.getType(), javaFieldTypeEnum.getType()+"");
        }
        return optionsBO;
    }

}
