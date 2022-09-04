package com.tianhua.springboot.dto;

import lombok.Data;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class UserDTO {
    public UserDTO(String userName,String aliasName,String country){
        this.userName = userName;
        this.aliasName = aliasName;
        this.country = country;
        this.currentAddress = country + aliasName;
        this.id = 100000L;
        this.age = 19;
    }

    private String userName;
    private String aliasName;
    private String country;
    private String currentAddress;
    private Long id;
    private int age;

    private String company;


}
