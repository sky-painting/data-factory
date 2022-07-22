package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.FieldExtBO;
import com.tianhua.datafactory.vo.model.FieldVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* @Description:paramConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface FieldConverter {
	FieldConverter INSTANCE = Mappers.getMapper(FieldConverter.class);

	/**
	 * vo列表转换成bo列表
	 * @Description:
	 * @return List<FieldBO>
	 */
	 List<FieldBO> VOs2BOs(List<FieldVO> vOList);

	/**
	 * vo转换成bo
	 * @Description:
	 * @return ParamModelBO
	 */
	@Mappings({
			@Mapping(target = "fieldExtBO",expression = "java(vo2boExt(fieldVO))"),
	})
	FieldBO vo2bo(FieldVO fieldVO);

	/**
	 * vo转换成bo
	 * @Description:
	 * @return ParamModelBO
	 */
	@Mappings({
			@Mapping(target = "defaultValueList",expression = "java(split(fieldVO.getDefaultValueList()))"),
	})
	FieldExtBO vo2boExt(FieldVO fieldVO);

	/**
	 * bo转换成vo
	 * @Description:
	 * @return fieldBO
	 */
	@Mappings({
			@Mapping(target = "dataSourceCode",expression = "java(fieldBO.getFieldExtBO().getDataSourceCode())"),
			@Mapping(target = "bizUnique",expression = "java(fieldBO.getFieldExtBO().getBizUnique())"),
			@Mapping(target = "bizIdentifier",expression = "java(fieldBO.getFieldExtBO().getBizIdentifier())"),
			@Mapping(target = "complexStruct",expression = "java(fieldBO.getFieldExtBO().getComplexStruct())"),
			@Mapping(target = "checkRule",expression = "java(fieldBO.getFieldExtBO().getCheckRule())"),
			@Mapping(target = "mappingDBColumn",expression = "java(fieldBO.getFieldExtBO().getMappingDBColumn())"),
			@Mapping(target = "defaultValueList",expression = "java(join(fieldBO.getFieldExtBO().getDefaultValueList()))"),

	})
	FieldVO bo2VO(FieldBO fieldBO);



	@Mappings({
			@Mapping(target = "defaultValueList",expression = "java(join(fieldExtBO.getDefaultValueList()))"),
	})
	FieldVO vo2boExt(FieldExtBO fieldExtBO);



	/**
	 * bo列表转换成vo列表
	 * @Description:
	 * @return List<FieldVO>
	 */
	 List<FieldVO> BOs2VOs(List<FieldBO> fieldBOList);

	 default String join(List<String> list){
		 if(CollectionUtils.isEmpty(list)){
			 return  "";
		 }
		 return StringUtils.join(list,",");
	 }


	default List<String> split(String str){
		if(StringUtils.isEmpty(str)){
			return Lists.newArrayList();
		}
		if(str.contains(",")){
			return Lists.newArrayList(StringUtils.split(",",str));
		}
		return Lists.newArrayList(str);
	}
}