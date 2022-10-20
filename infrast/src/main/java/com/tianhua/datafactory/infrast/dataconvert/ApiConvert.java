package com.tianhua.datafactory.infrast.dataconvert;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ApiModelDO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:api模型信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ApiConvert{
	ApiConvert INSTANCE = Mappers.getMapper(ApiConvert.class);

	/**
	 *
	 * @Description:
	 * @return ApiBO
	 */
	@Mappings({
			@Mapping(target = "paramList",expression = "java(com.alibaba.fastjson.JSON.parseArray(apiModelDO.getRequestParam(),com.tianhua.datafactory.domain.bo.model.ParamModelBO.class))"),
			@Mapping(target = "returnParamModel",expression = "java(com.alibaba.fastjson.JSON.parseObject(apiModelDO.getReturnParam(),com.tianhua.datafactory.domain.bo.model.ParamModelBO.class))"),
	})
	 ApiBO do2bo(ApiModelDO apiModelDO);
	/**
	 *
	 * @Description:
	 * @return List<ApiBO>
	 */
	 List<ApiBO> doList2boList(List<ApiModelDO> apiModelDOList);
	/**
	 *
	 * @Description:
	 * @return ApiModelDO
	 */
	@Mappings({
			@Mapping(target = "requestParam",expression = "java(com.alibaba.fastjson.JSON.toJSONString(apiBO.getParamList()))"),
			@Mapping(target = "returnParam",expression = "java(com.alibaba.fastjson.JSON.toJSONString(apiBO.getReturnParamModel()))"),
	})
	 ApiModelDO bo2do(ApiBO apiBO);
	/**
	 *
	 * @Description:
	 * @return List<ApiModelDO>
	 */
	 List<ApiModelDO> boList2doList(List<ApiBO> apiBOList);
}