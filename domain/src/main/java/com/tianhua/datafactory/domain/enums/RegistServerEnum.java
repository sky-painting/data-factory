package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * date: 2022/6/9
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum RegistServerEnum {
    //独立服务--无注册中心
    INDEPENDENCY_SERVICE(0,"Independency Service"),

    //nacos
    NACOS(1,"Nacos"),
    //apollo
    ZOOKEEPER(2,"Apollo"),
    //Eureka
    EUREKA(3,"Eureka"),
    //Consul
    CONSUL(4,"Consul"),

    //第三方服务
    THIRD_PARTY_SERVICE(5,"Third Party Service"),


    ;
    private Integer code;
    private String desc;

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (RegistServerEnum registServerEnum : RegistServerEnum.values()){
            optionsBO.addOptionItem(registServerEnum.getDesc(), registServerEnum.getCode()+"");
        }
        return optionsBO;
    }

    public static boolean isRegistServer(String enumCode){
        return "registServer".equals(enumCode);
    }

}
