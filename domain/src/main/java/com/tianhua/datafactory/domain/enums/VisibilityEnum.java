package com.tianhua.datafactory.domain.enums;

/**
 * Description:
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum VisibilityEnum {

    PRIVATE("private","-"),
    PROTECT("protected","#"),
    PUBLIC("public","+"),
    PACKAGE_PRIVATE("package private","~"),
    ;
    private String visibility;
    private String tag;
    VisibilityEnum(String visibility, String tag){
        this.visibility = visibility;
        this.tag = tag;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getTag() {
        return tag;
    }

    /**
     * 获取修饰符
     * @param content
     * @return
     */
    public static String getVisibilityStr(String content) {
        if (content.startsWith(VisibilityEnum.PUBLIC.tag)) {
            return VisibilityEnum.PUBLIC.visibility;
        }

        if (content.startsWith(VisibilityEnum.PRIVATE.tag)) {
            return VisibilityEnum.PRIVATE.visibility;
        }

        if (content.startsWith(VisibilityEnum.PROTECT.tag)) {
            return VisibilityEnum.PROTECT.visibility;
        }

        if (content.startsWith(VisibilityEnum.PACKAGE_PRIVATE.tag)) {
            return VisibilityEnum.PACKAGE_PRIVATE.visibility;
        }
        return  VisibilityEnum.PRIVATE.visibility;
    }

}
