package com.tianhua.datafactory.convert;

import java.util.List;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;

import com.tianhua.datafactory.vo.model.ParamModelVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:paramConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface ParamConverter{
	ParamConverter INSTANCE = Mappers.getMapper(ParamConverter.class);

	/**
	 *
	 * @Description:
	 * @return List<ParamModelBO>
	 */
	 List<ParamModelBO> VOs2BOs(List<ParamModelVO> vOList);
	/**
	 *
	 * @Description:
	 * @return ParamModelBO
	 */
	@Mappings({
			@Mapping(target = "fieldBeanList",expression = "java(FieldConverter.INSTANCE.VOs2BOs(paramModelVO.getFieldBeanList()))"),
	})
	 ParamModelBO vo2bo(ParamModelVO paramModelVO);
	/**
	 *
	 * @Description:
	 * @return ParamModelVO
	 */
	@Mappings({
			@Mapping(target = "fieldBeanList",expression = "java(FieldConverter.INSTANCE.BOs2VOs(paramModelBO.getFieldBeanList()))"),
	})
	 ParamModelVO bo2VO(ParamModelBO paramModelBO);
	/**
	 *
	 * @Description:
	 * @return List<ParamModelVO>
	 */
	 List<ParamModelVO> BOs2VOs(List<ParamModelBO> paramModelBOList);
}