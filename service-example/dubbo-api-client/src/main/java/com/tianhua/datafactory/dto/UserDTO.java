package com.tianhua.datafactory.dto;

import lombok.Data;

import java.util.Date;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class UserDTO {
    private Long id;

    private String userName;

    private String chineseName;

    private Integer age;
    //身高
    private Double hight;

    private String cardNumber;

    //银行卡号
    private String bankCount;

    //开户行名称
    private String bankName;

    //公司名称
    private String companyName;

    //毕业学校
    private String schoolName;

    //银行卡余额
    private Double bankMoney;

    private String password;

    private String email;

    private Long departId;

    //用户状态
    private Short status;

    //记录创建时间
    private Date createTime;


    //记录修改时间
    private Date updateTime;

    //入职时间
    private Date joinDate;



}


