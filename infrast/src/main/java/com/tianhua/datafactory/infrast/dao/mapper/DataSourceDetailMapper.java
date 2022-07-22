package com.tianhua.datafactory.infrast.dao.mapper;



import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDetailDO;

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
	 * @param @param dataSourceDetailDO
	 * @return int
	 * @throws
	 */
	public int insert(DataSourceDetailDO dataSourceDetailDO);

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
	 * @return ResultDataDto<DataSourceDetailDO>    返回类型
	 * @throws
	 */
	public DataSourceDetailDO getById(Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceDetailDO    返回类型
	 * @throws
	 */
	public List<DataSourceDetailDO>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param DataSourceDetailDO
	* @return int
	* @throws
	*/
	public int update(DataSourceDetailDO dataSourceDetailDO);


	/**
	 *
	 * @Title: getBydataSourceId
	 * @Description: 通过dataSourceId查询
	 * @author:
	 * @param @param dataSourceId
	 * @return ResultDataDto<DataSourceDetailDO>    返回类型
	 * @throws
	 */
	public DataSourceDetailDO getByDataSourceId(Long dataSourceId);

}