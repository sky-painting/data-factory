package com.tianhua.datafactory.infrast.dao.mapper;



import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceReqConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:数据源请求配置表
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */
public interface DataSourceReqConfigMapper {

	/**
	 * 
	 * @Title: save 
	 * @Description:新增或修改 
	 * @author: 
	 * @param dataSourceReqConfigDO
	 * @return int
	 * @throws
	 */
	public int insert(DataSourceReqConfigDO dataSourceReqConfigDO);

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
	public DataSourceReqConfigDO getById(Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceDetailDO    返回类型
	 * @throws
	 */
	public List<DataSourceReqConfigDO>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param DataSourceDetailDO
	* @return int
	* @throws
	*/
	public int update(DataSourceReqConfigDO dataSourceReqConfigDO);


	/**
	 *
	 * @Title: getBydataSourceId
	 * @Description: 通过dataSourceId查询
	 * @author:
	 * @param @param dataSourceId
	 * @return ResultDataDto<DataSourceDetailDO>    返回类型
	 * @throws
	 */
	public List<DataSourceReqConfigDO> getByDataSourceId(Long dataSourceId);

}