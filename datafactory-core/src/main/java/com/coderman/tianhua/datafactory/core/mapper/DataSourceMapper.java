package com.coderman.tianhua.datafactory.core.mapper;


import com.coderman.tianhua.datafactory.core.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:数据源管理表mapperDAO接口
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */
public interface DataSourceMapper{

	/**
	 * 
	 * @Title: save 
	 * @Description:新增或修改 
	 * @author: 
	 * @param @param dataSourceEntity
	 * @return int
	 * @throws
	 */
	public long insert(DataSourceEntity dataSourceEntity);

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
	 * @return ResultDataDto<DataSourceEntity>    返回类型
	 * @throws
	 */
	public DataSourceEntity getById(@Param(value = "id") Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceEntity    返回类型
	 * @throws
	 */
	public List<DataSourceEntity>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param dataSourceEntity
	* @return int
	* @throws
	*/
	public int update(DataSourceEntity dataSourceEntity);


	/**
	 *
	 * @Title: getBysourceCode
	 * @Description: 通过sourceCode查询
	 * @author:
	 * @param @param id
	 * @return ResultDataDto<DataSourceEntity>    返回类型
	 * @throws
	 */
	public DataSourceEntity getBySourceCode(String sourceCode);
	
}