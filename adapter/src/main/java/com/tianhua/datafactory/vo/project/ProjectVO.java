package com.tianhua.datafactory.vo.project;


import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.vo.BaseVO;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Description:项目基本信息类
 * @Author:
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ProjectVO extends BaseVO {


    /** 项目应用名 **/
    private  String projectCode;

    /** 应用描述 **/
    private  String projectDesc;

    /** 业务领域编码 **/
    private  String domainCode;

    /** 业务领域描述 **/
    private  String domainDesc;

    /** app描述 **/
    private  String appDesc;

    /** 所属业务线 **/
    private  String busLine;

    /** 所属部门 **/
    private  String deptName;

    /** 项目api列表 **/
    private List<ApiBO> apiList;

    private Long id;

    private String file;

}