package com.tianhua.datafactory.vo;

import com.coderman.utils.response.ResultDataDto;
import lombok.Data;

/**
 * Description
 * 适配amis的接口返回契约
 *
 * https://baidu.github.io/amis/zh-CN/docs/types/api
 *
 * date: 2022/9/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class ResultAmisVO<T> extends ResultDataDto {
    private Integer status;
}
