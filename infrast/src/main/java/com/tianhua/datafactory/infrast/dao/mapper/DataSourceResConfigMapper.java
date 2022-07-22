package com.tianhua.datafactory.infrast.dao.mapper;



import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceRespConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:数据源响应配置表
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */
public interface DataSourceResConfigMapper {

	/**
	 * 
	 * @Title: save 
	 * @Description:新增或修改 
	 * @author: 
	 * @param dataSourceRespConfigDO
	 * @return int
	 * @throws
	 */
	public int insert(DataSourceRespConfigDO dataSourceRespConfigDO);

	/**
	 * 
	 * @Title: delete 
	 * @Description: 通过id删除数据
	 * @author: 
	 * @param @param id
	 * @return int
	 * @throws
	 */
	public int deleteByDataSourceId(@Param(value = "dataSourceId") Long dataSourceId);

	/**
	 * 
	 * @Title: getById 
	 * @Description: 通过id查询
	 * @author: 
	 * @param @param id
	 * @return ResultDataDto<DataSourceDetailDO>    返回类型
	 * @throws
	 */
	public DataSourceRespConfigDO getById(Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceDetailDO    返回类型
	 * @throws
	 */
	public List<DataSourceRespConfigDO>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param DataSourceDetailDO
	* @return int
	* @throws
	*/
	public int update(DataSourceRespConfigDO dataSourceRespConfigDO);


	/**
	 *
	 * @Title: getBydataSourceId
	 * @Description: 通过dataSourceId查询
	 * @author:
	 * @param  dataSourceId
	 * @return ResultDataDto<DataSourceDetailDO>    返回类型
	 * @throws
	 */
	public List<DataSourceRespConfigDO> getByDataSourceId(Long dataSourceId);

}