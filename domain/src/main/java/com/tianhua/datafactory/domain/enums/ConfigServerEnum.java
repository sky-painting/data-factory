package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 * date: 2022/5/30
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Getter
@AllArgsConstructor
public enum ConfigServerEnum {

    //nacos
    NACOS("0","nacos"),
    //apollo
    APOLLO("1","apollo"),

    ;
    private String code;
    private String desc;

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (ConfigServerEnum configServerEnum : ConfigServerEnum.values()){
            optionsBO.addOptionItem(configServerEnum.getDesc(), configServerEnum.getCode());
        }
        return optionsBO;
    }

}
