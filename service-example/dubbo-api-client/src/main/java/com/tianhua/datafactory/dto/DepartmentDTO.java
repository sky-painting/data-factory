package com.tianhua.datafactory.dto;

import lombok.Data;

import java.util.List;

/**
 * Description
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class DepartmentDTO {
    private Long id;

    private String departName;

    private Long managerId;

    private Long superId;

    private Integer grade;

    private List<DepartmentDTO> subDepartList;
}
