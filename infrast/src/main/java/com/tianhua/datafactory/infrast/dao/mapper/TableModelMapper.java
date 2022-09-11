package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.infrast.dao.dataobject.TableModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:表模型mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface TableModelMapper{

	/**
	 * @Description:新增或修改
	 * @param tableModelDO
	 * @return int
	 */
	public long insert(TableModelDO tableModelDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<TableModelDO>
	 */
	public TableModelDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<TableModelDO
	 */
	public List<TableModelDO>  getAll();


	public List<TableModelDO> getByProjectCode(String projectCode);

	/**
	 * @Description:新增或修改
	 * @param tableModelDO
	 * @return int
	 */
	public int update(TableModelDO tableModelDO);

	/**
	 *
	 * @param page
	 * @return
	 */
	public List<TableModelDO>  getPageList(@Param(value = "page") PageBean page);


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getPageCount(@Param(value = "page") PageBean page);


	List<TableModelDO> search(String content);
}