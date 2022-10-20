package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ModelSuffixConfigDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Description:后缀配置表mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface ModelSuffixConfigMapper{

	/**
	 * @Description:新增或修改
	 * @param modelSuffixConfigDO
	 * @return int
	 */
	public long insert(ModelSuffixConfigDO modelSuffixConfigDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<ModelSuffixConfigDO>
	 */
	public ModelSuffixConfigDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<ModelSuffixConfigDO
	 */
	public List<ModelSuffixConfigDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param modelSuffixConfigDO
	 * @return int
	 */
	public int update(ModelSuffixConfigDO modelSuffixConfigDO);

	/**
	 * @Description:查询所有数据
	 * @return List<ModelSuffixConfigDO
	 */
	public List<ModelSuffixConfigDO>  getPageList();


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getCount();

}