package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;

import java.util.List;


/**
 * @Description:项目api查询仓库接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:05:26
 * @version v1.0
 */
public interface ProjectQueryRepository{

	/**
	 *
	 * @Description 获取api信息
	 * @param projectCode
	 * @return List<ApiBO>
	 */
     List<ApiBO> getApiListByCode(String projectCode);

	/**
	 *
	 * @Description 搜索api信息
	 * @param content
	 * @return List<ApiBO>
	 */
     List<ApiBO> searchApi(String content);

	/**
	 *
	 * @Description 分页查询api列表
	 * @param pageBean
	 * @return PageBean
	 */
	PageBean queryApiPage(PageBean pageBean );

	/**
	 *
	 * @Description 获取单条api
	 * @param methodSign
	 * @return ApiBO
	 */
     ApiBO getBySign(String methodSign);

	/**
	 *
	 * @Description 获取项目信息
	 * @param projectCode
	 * @return ProjectBO
	 */
     ProjectBO getProjectByCode(String projectCode);

	/**
	 *
	 * @Description 搜索项目信息
	 * @param content
	 * @return List<ProjectBO>
	 */
     List<ProjectBO> searchProject(String content);

	/**
	 *
	 * @Description 分页查询项目列表
	 * @param pageBean
	 * @return List<ProjectBO>
	 */
	PageBean queryProjectPage(PageBean pageBean);



	ApiBO getApiById(Long id);
}