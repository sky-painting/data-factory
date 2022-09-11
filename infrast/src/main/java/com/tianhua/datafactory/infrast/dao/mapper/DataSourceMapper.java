package com.tianhua.datafactory.infrast.dao.mapper;


import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDO;
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
	 * @param @param dataSourceDO
	 * @return int
	 * @throws
	 */
	public long insert(DataSourceDO dataSourceDO);

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
	public DataSourceDO getById(@Param(value = "id") Long id);

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return List<DataSourceEntity    返回类型
	 * @throws
	 */
	public List<DataSourceDO>  getAll();

	/**
	*
	* @Title: update
	* @Description:新增或修改
	* @author:
	* @param @param dataSourceEntity
	* @return int
	* @throws
	*/
	public int update(DataSourceDO dataSourceEntity);


	/**
	 *
	 * @Title: getBysourceCode
	 * @Description: 通过sourceCode查询
	 * @author:
	 * @param @param id
	 * @return ResultDataDto<DataSourceEntity>    返回类型
	 * @throws
	 */
	public DataSourceDO getBySourceCode(String sourceCode);

	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatus(@Param(value = "id") Long id,@Param(value = "status") Integer status);

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<DataSourceDO> getPage(@Param(value = "page") PageBean page);


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page") PageBean page);


	public List<DataSourceDO> search(@Param(value = "content") String content);



}