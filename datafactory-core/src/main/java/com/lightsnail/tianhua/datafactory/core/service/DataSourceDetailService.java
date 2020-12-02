package com.lightsnail.tianhua.datafactory.core.service;

import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.lightsnail.tianhua.datafactory.core.vo.DataSourceDetailVO;

import java.util.List;

/**
 * @Description:数据源详情表Service接口
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */
public interface DataSourceDetailService{

	/**
	 *
	 * @Title: save
	 * @Description:新增
	 * @author:
	 * @param @param dto
	 * @param @param userId
	 * @return ResultDto    返回类型
	 * @throws
	 */
	public ResultDto save(DataSourceDetailVO dataSourceDetailVo) throws Exception;

	/**
	 * 
	 * @Title: delete 
	 * @Description: 通过id删除数据
	 * @author: 
	 * @param @param id
	 * @return ResultDto    返回类型 
	 * @throws
	 */
	public ResultDto delete(Long id);

	/**
	 * 
	 * @Title: getById 
	 * @Description: 通过id查询
	 * @author: 
	 * @param @param id
	 * @return ResultDataDto<DataSourceDetailVO>
	 * @throws
	 */
	public ResultDataDto<DataSourceDetailVO> getById(Long id) throws Exception;

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return ResultDataDto<List<DataSourceDetailVO>>
	 * @throws
	 */
	public ResultDataDto<List<DataSourceDetailVO>> getAll() throws Exception;

	/**
	*
	* @Title: update
	* @Description:修改
	* @author:
	* @param @param dataSourceDetailEntity
	* @return int
	* @throws
	*/
	public ResultDto update(DataSourceDetailVO dataSourceDetailVo) throws Exception;

}