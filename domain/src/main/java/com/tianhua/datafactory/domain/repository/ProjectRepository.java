package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.project.ProjectBO;


/**
 * @Description:项目api仓库接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:05:26
 * @version v1.0
 */
public interface ProjectRepository{

	/**
	 *
	 * @Description 保存项目&api
	 * @param projectBO
	 * @return boolean
	 */
     boolean saveProject(ProjectBO projectBO);

	/**
	 *
	 * @Description 修改项目&api
	 * @param projectBO
	 * @return boolean
	 */
     boolean updateProject(ProjectBO projectBO);

	/**
	 *
	 * @Description 获取项目信息聚合接口
	 * @param projectCode
	 * @return ProjectBO
	 */
     ProjectBO getByCode(String projectCode) throws Exception;



}