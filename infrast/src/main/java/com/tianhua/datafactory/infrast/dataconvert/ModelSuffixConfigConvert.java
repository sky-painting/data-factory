package com.tianhua.datafactory.infrast.dataconvert;

import java.util.List;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.infrast.dao.dataobject.ModelSuffixConfigDO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:模型后缀信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ModelSuffixConfigConvert{
	ModelSuffixConfigConvert INSTANCE = Mappers.getMapper(ModelSuffixConfigConvert.class);

	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigBO
	 */
	 ModelSuffixConfigBO do2bo(ModelSuffixConfigDO modelSuffixConfigDO);
	/**
	 *
	 * @Description:
	 * @return List<ModelSuffixConfigBO>
	 */
	 List<ModelSuffixConfigBO> doList2boList(List<ModelSuffixConfigDO> modelSuffixConfigDOList);
	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigDO
	 */
	 ModelSuffixConfigDO bo2do(ModelSuffixConfigBO modelSuffixConfigBO);
	/**
	 *
	 * @Description:
	 * @return List<ModelSuffixConfigDO>
	 */
	 List<ModelSuffixConfigDO> boList2doList(List<ModelSuffixConfigBO> modelSuffixConfigBOList);
}