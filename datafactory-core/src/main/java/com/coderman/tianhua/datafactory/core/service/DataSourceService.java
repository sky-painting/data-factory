package com.coderman.tianhua.datafactory.core.service;

import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceQueryDTO;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;

import java.util.List;

/**
 * @Description:数据源管理表Service接口
 * @Author:fanchunshuai
 * @CreateTime:2020-12-02 23:07:13
 * @version v1.0
 */

public interface DataSourceService {

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
	public ResultDto save(DataSourceVO dataSourceVo) throws Exception;

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
	 * @return ResultDataDto<DataSourceVO>
	 * @throws
	 */
	public ResultDataDto<DataSourceVO> getById(Long id) throws Exception;

	/**
	 * 
	 * @Title: getAll 
	 * @Description:查询所有数据 
	 * @author: 
	 * @return ResultDataDto<List<DataSourceVO>>
	 * @throws
	 */
	public ResultDataDto<List<DataSourceVO>> getAll() throws Exception;

	/**
	*
	* @Title: update
	* @Description:修改
	* @author:
	* @param @param dataSourceEntity
	* @return int
	* @throws
	*/
	public ResultDto update(DataSourceVO dataSourceVo) throws Exception;

	/**
	 * 根据数据源code获取数据源详情-包括数据源信息
	 * @param dataSourceFieldRequestBean
	 * @return
	 * @throws Exception
	 */
	public ResultDataDto<String> getDataSourceDetail(DataSourceFieldRequestBean dataSourceFieldRequestBean ) throws Exception;


	/**
	 * 修改数据源状态
	 * @param id
	 * @param status
	 * @return
	 */
	public ResultDto updateStatus(Long id,Integer status);

	/**
	 * 分页查询
	 * @param dataSourceQueryDTO
	 * @return
	 */
	public ResultDataDto<List<DataSourceVO>> getPage(DataSourceQueryDTO dataSourceQueryDTO);

}