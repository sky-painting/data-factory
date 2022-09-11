package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.infrast.dao.dataobject.ProjectConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:项目配置mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ProjectConfigMapper{

	/**
	 * @Description:新增或修改
	 * @param projectConfigDO
	 * @return int
	 */
	public long insert(ProjectConfigDO projectConfigDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ProjectConfigDO>
	 */
	public ProjectConfigDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<ProjectConfigDO
	 */
	public List<ProjectConfigDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param projectConfigDO
	 * @return int
	 */
	public int update(ProjectConfigDO projectConfigDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ProjectConfigDO
	 */
	public List<ProjectConfigDO>  getPageList(@Param(value = "page") PageBean page );


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page") PageBean page );


	public ProjectConfigDO getByCode(String projectCode);


	public List<ProjectConfigDO> search(@Param(value = "content") String content);
}