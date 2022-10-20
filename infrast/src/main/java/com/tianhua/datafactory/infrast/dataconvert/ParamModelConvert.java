package com.tianhua.datafactory.infrast.dataconvert;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ParamModelDO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:参数模型信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ParamModelConvert{
	ParamModelConvert INSTANCE = Mappers.getMapper(ParamModelConvert.class);

	/**
	 *
	 * @Description:
	 * @return ParamModelBO
	 */
	 ParamModelBO do2bo(ParamModelDO paramModelDO);
	/**
	 *
	 * @Description:
	 * @return List<ParamModelBO>
	 */
	 List<ParamModelBO> doList2boList(List<ParamModelDO> paramModelDOList);
	/**
	 *
	 * @Description:
	 * @return ParamModelDO
	 */
	 ParamModelDO bo2do(ParamModelBO paramModelBO);
	/**
	 *
	 * @Description:
	 * @return List<ParamModelDO>
	 */
	 List<ParamModelDO> boList2doList(List<ParamModelBO> paramModelBOList);
}