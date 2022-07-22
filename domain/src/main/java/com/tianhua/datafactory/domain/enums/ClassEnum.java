package com.tianhua.datafactory.domain.enums;

import java.util.Objects;

/**
 * Description:
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum ClassEnum {
    ENTITY("entity"),
    CLASS("class"),
    INTERFACE("interface"),
    ENUM("enum"),
    POM("pom"),
    PROPERTIES("properties"),
    YML("YML"),

    ;
    private String classType;
    ClassEnum(String classType){
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public static boolean isClass(String classType){
        return Objects.equals(classType,ClassEnum.CLASS.getClassType());
    }

    public static boolean isInterface(String classType){
        return Objects.equals(classType,ClassEnum.INTERFACE.getClassType());
    }

    public static boolean isEnum(String classType){
        return Objects.equals(classType,ClassEnum.ENUM.getClassType());
    }

    public static boolean isPom(String classType){
        return Objects.equals(classType,ClassEnum.POM.getClassType());
    }


}
