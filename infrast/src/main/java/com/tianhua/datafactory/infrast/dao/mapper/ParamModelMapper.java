package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.infrast.dao.dataobject.ParamModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:参数模型表mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ParamModelMapper{

	/**
	 * @Description:新增或修改
	 * @param paramModelDO
	 * @return int
	 */
	public long insert(ParamModelDO paramModelDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ParamModelDO>
	 */
	public ParamModelDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<ParamModelDO
	 */
	public List<ParamModelDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param paramModelDO
	 * @return int
	 */
	public int update(ParamModelDO paramModelDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ParamModelDO
	 */
	public List<ParamModelDO>  getPageList(@Param(value = "page") PageBean page);


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page") PageBean page);


	public List<ParamModelDO> getByProjectCode(String projectCode);


	public List<ParamModelDO> search(String content);
}