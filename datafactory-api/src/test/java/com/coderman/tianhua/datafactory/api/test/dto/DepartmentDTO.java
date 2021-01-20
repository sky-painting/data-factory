package com.coderman.tianhua.datafactory.api.test.dto;

import lombok.Data;

/**
 * description: DepartmentDTO <br>
 * date: 2021/1/20 23:02 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
public class DepartmentDTO {
    private Long id;
    /**
     * 部门名称
     */
    private String departName;
    /**
     * 父级部门id
     */
    private Long superId;
    /**
     * 部门负责人id
     */
    private Long managerId;


}
