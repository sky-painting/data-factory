package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.infrast.dao.dataobject.ApiModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:API数据表mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ApiModelMapper{

	/**
	 * @Description:新增或修改
	 * @param apiModelDO
	 * @return int
	 */
	public long insert(ApiModelDO apiModelDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ApiModelDO>
	 */
	public ApiModelDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<ApiModelDO
	 */
	public List<ApiModelDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param apiModelDO
	 * @return int
	 */
	public int update(ApiModelDO apiModelDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ApiModelDO
	 */
	public List<ApiModelDO>  getPageList(@Param(value = "page") PageBean page );


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page") PageBean page );


	public List<ApiModelDO>  getByProjectCode(String projectCode);



	public void deleteByProjectCode(String projectCode);


	public ApiModelDO getByApiSign(String apiSign);

}