package com.tianhua.datafactory.infrast.dao.mapper;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.FieldModelDO;
import com.tianhua.datafactory.infrast.dao.dataobject.ParamModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Description:属性模型mapperDAO接口
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:29:46
 * @version v1.0
 */
@Mapper
public interface FieldModelMapper{

	/**
	 * @Description:新增或修改
	 * @param fieldModelDO
	 * @return int
	 */
	public long insert(FieldModelDO fieldModelDO);

	/**
	 * @Description: 通过id删除数据
	 * @param id
	 * @return int
	 */
	public int deleteById(Long id);

	/**
	 * @Description: 通过id查询
	 * @param id
	 * @return ResultDataDto<FieldModelDO>
	 */
	public FieldModelDO getById(Long id);

	/**
	 * @Description:查询所有数据 
	 * @return List<FieldModelDO
	 */
	public List<FieldModelDO>  getAll();

	/**
	 * @Description:新增或修改
	 * @param fieldModelDO
	 * @return int
	 */
	public int update(FieldModelDO fieldModelDO);

	/**
	 * @Description:查询所有数据
	 * @return List<FieldModelDO
	 */
	public List<FieldModelDO>  getPageList();


	/**
	 * @Description:查询数量
	 * @return int
	 */
	public int  getCount();

	public int deleteByProjectCodeAndParamClassName(@Param(value = "projectCode") String projectCode,@Param(value = "paramClassName") String paramClassName);


	/**
	 * 根据项目名称和模型名称获取属性列表
	 * @param projectCode
	 * @param paramClassName
	 * @return
	 */
	public List<FieldModelDO> getByCode(String projectCode, String paramClassName);
}