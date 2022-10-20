package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;
import com.tianhua.datafactory.vo.model.ModelMappingVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* @Description:ModelSuffixConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface ModelMappingConverter {
	ModelMappingConverter INSTANCE = Mappers.getMapper(ModelMappingConverter.class);

	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigBO
	 */
	 ModelMappingBO vo2bo(ModelMappingVO modelMappingVO);
	/**
	 *
	 * @Description:
	 * @return List<ModelSuffixConfigVO>
	 */
	 List<ModelMappingVO> BOs2VOs(List<ModelMappingBO> modelSuffixConfigBOList);
	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigVO
	 */
	ModelMappingVO bo2VO(ModelMappingBO modelMappingBO);
	/**
	 *
	 * @Description:
	 * @return List<ModelMappingBO>
	 */
	 List<ModelMappingBO> VOs2BOs(List<ModelMappingVO> vOList);
}