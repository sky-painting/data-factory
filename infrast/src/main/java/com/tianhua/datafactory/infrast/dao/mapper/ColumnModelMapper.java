package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ColumnModelDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Description:字段模型mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ColumnModelMapper{

	/**
	 * @Description:新增或修改
	 * @param columnModelDO
	 * @return int
	 */
	public long insert(ColumnModelDO columnModelDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ColumnModelDO>
	 */
	public ColumnModelDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<ColumnModelDO
	 */
	public List<ColumnModelDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param columnModelDO
	 * @return int
	 */
	public int update(ColumnModelDO columnModelDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ColumnModelDO
	 */
	public List<ColumnModelDO>  getPageList();


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getCount();

	void deleteByTableId(Long tableId);


	List<ColumnModelDO> getByTableId(Long tableId);
}