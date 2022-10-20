package com.tianhua.datafactory.convert;

import java.util.List;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;

import com.tianhua.datafactory.vo.model.ModelSuffixConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:ModelSuffixConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface ModelSuffixConverter{
	ModelSuffixConverter INSTANCE = Mappers.getMapper(ModelSuffixConverter.class);

	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigBO
	 */
	 ModelSuffixConfigBO vo2bo(ModelSuffixConfigVO modelSuffixConfigVO);
	/**
	 *
	 * @Description:
	 * @return List<ModelSuffixConfigVO>
	 */
	 List<ModelSuffixConfigVO> BOs2VOs(List<ModelSuffixConfigBO> modelSuffixConfigBOList);
	/**
	 *
	 * @Description:
	 * @return ModelSuffixConfigVO
	 */
	 ModelSuffixConfigVO bo2VO(ModelSuffixConfigBO modelSuffixConfigBO);
	/**
	 *
	 * @Description:
	 * @return List<ModelSuffixConfigBO>
	 */
	 List<ModelSuffixConfigBO> VOs2BOs(List<ModelSuffixConfigVO> vOList);
}