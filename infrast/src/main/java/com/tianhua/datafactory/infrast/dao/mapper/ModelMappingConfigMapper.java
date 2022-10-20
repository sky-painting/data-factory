package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.infrast.dao.dataobject.ModelMappingConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:模型映射表mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ModelMappingConfigMapper{

	/**
	 * @Description:新增或修改
	 * @param modelMappingConfigDO
	 * @return int
	 */
	public long insert(ModelMappingConfigDO modelMappingConfigDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ModelMappingConfigDO>
	 */
	public ModelMappingConfigDO getById(Long id);


	List<ModelMappingConfigDO> getListByProjectCode(@Param(value = "projectCode") String projectCode);

	/**
	 * @Description:查询所有数据 
	 * @return List<ModelMappingConfigDO
	 */
	public List<ModelMappingConfigDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param modelMappingConfigDO
	 * @return int
	 */
	public int update(ModelMappingConfigDO modelMappingConfigDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ModelMappingConfigDO
	 */
	public List<ModelMappingConfigDO>  getPageList(@Param(value = "page")PageBean page);


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page")PageBean page);

}