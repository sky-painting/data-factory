package com.tianhua.datafactory.domain.enums;

import com.tianhua.datafactory.domain.support.OptionsBO;
import lombok.Getter;

/**
 * Description
 * api模型,参数模型,属性模型的生命周期状态管理
 * date: 2022/9/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 *
 * 状态跳转 0->2->1->-1的规则
 */
@Getter
public enum ApiModelFieldStatusEnum {
    USING(0,"正常"),
    DEPRECATED(1,"即将过期"),
    WILL_DEPRECATED(2,"已过期"),
    DELETEED(-1,"删除"),

    ;
    private Integer status;
    private String desc;

    ApiModelFieldStatusEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }


    public static boolean isApiModelFieldStatus(String enumCode){
        return "apiModelFieldStatus".equals(enumCode);
    }

    public static OptionsBO getOptionList(){
        OptionsBO optionsBO = new OptionsBO();
        for (ApiModelFieldStatusEnum apiModelFieldStatusEnum : ApiModelFieldStatusEnum.values()){
            optionsBO.addOptionItem(apiModelFieldStatusEnum.getDesc(), apiModelFieldStatusEnum.getStatus()+"");
        }
        return optionsBO;
    }


    public static String getStatusDesc(Integer status){
        if(status == null){
            status = 0;
        }
        for (ApiModelFieldStatusEnum apiModelFieldStatusEnum : ApiModelFieldStatusEnum.values()){
            if(apiModelFieldStatusEnum.getStatus().intValue() == status.intValue()){
                return apiModelFieldStatusEnum.getDesc();
            }
        }
        return ApiModelFieldStatusEnum.USING.getDesc();
    }



}
