package com.tianhua.datafactory.infrast.dataconvert;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ModelMappingConfigDO;
import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:模型映射信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ModelMappingConvert{
	ModelMappingConvert INSTANCE = Mappers.getMapper(ModelMappingConvert.class);

	/**
	 *
	 * @Description:
	 * @return ModelMappingBO
	 */
	 ModelMappingBO do2bo(ModelMappingConfigDO modelMappingConfigDO);
	/**
	 *
	 * @Description:
	 * @return List<ModelMappingBO>
	 */
	 List<ModelMappingBO> doList2boList(List<ModelMappingConfigDO> modelMappingConfigDOList);
	/**
	 *
	 * @Description:
	 * @return ModelMappingConfigDO
	 */
	 ModelMappingConfigDO bo2do(ModelMappingBO modelMappingBO);
	/**
	 *
	 * @Description:
	 * @return List<ModelMappingConfigDO>
	 */
	 List<ModelMappingConfigDO> boList2doList(List<ModelMappingBO> modelMappingBOList);
}