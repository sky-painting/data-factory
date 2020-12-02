package com.coderman.tianhua.datafactory.core.mapper;


import com.coderman.tianhua.datafactory.core.entity.DataSourceDetailEntity;

import java.util.List;


/**
 * @Description:数据源详情表mapperDAO接口
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */
public interface DataSourceDetailMapper{

	/**
	 * 
	 * @Title: save 
	 * @Description:新增或修改 
	 * @author: 
	 * @param @param dataSourceDetailEntity
	 * @return int
	 * @throws
	 */
	public int insert(DataSourceDetailEntity dataSourceDetailEntity);

	/**
	 * 
	 * @Title: delete 
	 * @Description: 通过id删除数据
	 * @author: 
	 * @param @param id
	 * @return int
	 * @throws
	 */
	public int deleteById(Long id);

	/**
	 * 
	 * @Title: getById 
	 * @Description: 通过id查询
	 * @author: 
	 * @param @param id
	 * @return ResultDataDto<DataSourceDetailEntity>    返回类型
	 * @throws
	 */
	public DataSourceDetailEntity getById(Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceDetailEntity    返回类型
	 * @throws
	 */
	public List<DataSourceDetailEntity>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param dataSourceDetailEntity
	* @return int
	* @throws
	*/
	public int update(DataSourceDetailEntity dataSourceDetailEntity);
	
}