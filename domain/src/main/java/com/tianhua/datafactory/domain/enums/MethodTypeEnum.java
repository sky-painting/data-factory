package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.Getter;

/**
 * Description:
 * date: 2022/6/7
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Getter
public enum MethodTypeEnum {
    /**
     *
     */
    HTTP_POST("post","http-post方法"),
    HTTP_GET("get","http-get方法"),
    HTTP_PUT("put","http-put方法"),
    HTTP_DELETE("delete","http-delete方法"),

    DUBBO("DUBBO","dubbo服务接口"),
    SPRING_CLOUD("SPRING_CLOUD","spring_cloud服务接口"),
    INNER_SERVICE("INNER_SERVICE","内部服务方法"),

    ;

    /** api类型code **/
    private String type;
    /** api类型描述 **/
    private String desc;


    MethodTypeEnum(String type,String desc){
        this.type = type;
        this.desc = desc;
    }


    public static boolean isMethodType(String enumCode){
        return "methodType".equals(enumCode);
    }




    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (MethodTypeEnum apiTypeEnum : MethodTypeEnum.values()){
            optionsBO.addOptionItem(apiTypeEnum.getDesc(), apiTypeEnum.getType());
        }
        return optionsBO;
    }


    public static boolean isApiType(String enumCode){
        return "apiType".equals(enumCode);
    }

}
