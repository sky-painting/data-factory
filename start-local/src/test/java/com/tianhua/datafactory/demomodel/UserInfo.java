package com.tianhua.datafactory.demomodel;

import lombok.Builder;
import lombok.Data;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
@Builder
public class UserInfo {
    private Long id;
    private String userName;
    private Integer age;
    private String city;
    private String company;
}
