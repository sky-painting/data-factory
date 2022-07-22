package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.model.TableBO;

import java.util.List;


/**
 * @Description:项目模型仓库接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:05:26
 * @version v1.0
 */
public interface ModelRepository{

	/**
	 *
	 * @Description 保存数据库模型
	 * @param tableBO
	 * @return boolean
	 */
     boolean saveDBModel(TableBO tableBO);

	/**
	 *
	 * @Description 修改数据库模型
	 * @param tableBO
	 * @return boolean
	 */
     boolean updateDBModel(TableBO tableBO);

	/**
	 *
	 * @Description 获取数据库模型聚合接口
	 * @param projectCode
	 * @return List<TableBO>
	 */
     List<TableBO> getDbErByProjectCode(String projectCode);

	/**
	 *
	 * @Description 保存参数模型
	 * @param paramModelBO
	 * @return boolean
	 */
     boolean saveParamModel(ParamModelBO paramModelBO);

	/**
	 *
	 * @Description 修改参数模型
	 * @param paramModelBO
	 * @return boolean
	 */
     boolean updateParamModel(ParamModelBO paramModelBO);

	/**
	 *
	 * @Description 获取参数模型聚合接口
	 * @param projectCode
	 * @return List<ParamModelBO>
	 */
     List<ParamModelBO> getModelByProjectCode(String projectCode);

	/**
	 *
	 * @Description 保存模型后缀信息
	 * @param modelSuffixConfigBO
	 * @return boolean
	 */
     boolean saveModelSuffix(ModelSuffixConfigBO modelSuffixConfigBO);

	/**
	 *
	 * @Description 修改模型后缀信息
	 * @param modelSuffixConfigBO
	 * @return boolean
	 */
     boolean updateModelSuffix(ModelSuffixConfigBO modelSuffixConfigBO);


	/**
	 * 保存模型映射关系
	 * @param modelMappingBO
	 * @return
	 */
	boolean saveModelMapping(ModelMappingBO modelMappingBO);


}