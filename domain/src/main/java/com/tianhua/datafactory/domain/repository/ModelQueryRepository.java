package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.model.*;

import java.util.List;


/**
 * @Description:项目模型查询仓库接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:05:26
 * @version v1.0
 */
public interface ModelQueryRepository{

	/**
	 *
	 * @Description 获取单条参数信息
	 * @param paramClassName
	 * @return ParamModelBO
	 */
     ParamModelBO getByParamClassName(String paramClassName);

	/**
	 *
	 * @Description 根据项目和表名获取数据库字段列表
	 * @param projectCode
	 * @param tableName
	 * @return List<ColumnBO>
	 */
     List<ColumnBO> getColumnListByCode(String projectCode, String tableName);

	/**
	 *
	 * @Description 分页查询表结构列表
	 * @param pageBean
	 * @return PageBean
	 */
	PageBean queryTablePage(PageBean pageBean );


	/**
	 *
	 * @Description 分页查询业务模型
	 * @param pageBean
	 * @return PageBean
	 */
	PageBean queryParamPage(PageBean pageBean );



	TableBO getByTableId(Long id);


	ParamModelBO getByParamId(Long id);


	/**
	 * 搜索表模型
	 * @param content
	 * @return
	 */
	List<TableBO> searchTable(String content);

	/**
	 * 搜索业务模型
	 * @param content
	 * @return
	 */
	List<ParamModelBO> searchParamModel(String content);


	/**
	 * 获取项目模型配置
	 * @return
	 */
	List<ModelSuffixConfigBO> getModelSuffixConfigList();


	/**
	 *
	 * @Description 分页查询模型映射关系
	 * @param pageBean
	 * @return PageBean
	 */
	PageBean queryModelMappingPage(PageBean pageBean );


	/**
	 * 表模型+参数模型
	 * @param projectCode
	 * @return
	 */
	List<ParamModelBO> getModelByProjectCode(String projectCode);


	/**
	 * 表模型+参数模型
	 * @param projectCode
	 * @param modelName
	 * @return
	 */
	List<FieldBO> getModelField(String projectCode, String modelName);


	/**
	 * 根据项目获取modelmapping映射列表
	 * @param projectCode
	 * @return
	 */
	List<ModelMappingBO> getModelMappingListByProjectCode(String projectCode);


}