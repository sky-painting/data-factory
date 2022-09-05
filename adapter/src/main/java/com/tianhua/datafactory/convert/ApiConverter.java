package com.tianhua.datafactory.convert;

import java.util.List;
import com.tianhua.datafactory.domain.bo.project.ApiBO;

import com.tianhua.datafactory.vo.project.ApiVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:ApiConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface ApiConverter{
	ApiConverter INSTANCE = Mappers.getMapper(ApiConverter.class);

	/**
	 *
	 * @Description:
	 * @return ApiVO
	 */
	@Mappings({
			@Mapping(target = "apiReturnWrapTypeDesc",expression = "java(com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum.getDesc(apiBO.getApiReturnWrapType()))"),
			@Mapping(target = "statusDesc",expression = "java(com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum.getStatusDesc(apiBO.getStatus()))"),
	})
	 ApiVO bo2VO(ApiBO apiBO);
	/**
	 *
	 * @Description:
	 * @return ApiBO
	 */
	 @Mappings({
			 @Mapping(target = "requestParam",expression = "java(com.alibaba.fastjson.JSON.toJSONString(apiVO.getParamList()))"),

	 })
	 ApiBO vo2bo(ApiVO apiVO);
	/**
	 *
	 * @Description:
	 * @return List<ApiVO>
	 */
	 List<ApiVO> BOs2VOs(List<ApiBO> apiBOList);
	/**
	 *
	 * @Description:
	 * @return List<ApiBO>
	 */
	 List<ApiBO> VOs2BOs(List<ApiVO> vOList);
}