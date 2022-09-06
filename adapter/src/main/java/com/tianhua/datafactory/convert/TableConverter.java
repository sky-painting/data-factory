package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.model.TableBO;
import java.util.List;

import com.tianhua.datafactory.vo.model.TableVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:tableConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface TableConverter{
	TableConverter INSTANCE = Mappers.getMapper(TableConverter.class);

	/**
	 *
	 * @Description:
	 * @return List<TableVO>
	 */
	 List<TableVO> BOs2VOs(List<TableBO> tableBOList);
	/**
	 *
	 * @Description:
	 * @return TableBO
	 */
	 TableBO vo2bo(TableVO tableVO);
	/**
	 *
	 * @Description:
	 * @return TableVO
	 */
	@Mappings({
			@Mapping(target = "statusDesc",expression = "java(com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum.getStatusDesc(tableBO.getStatus()))"),
	})
	 TableVO bo2VO(TableBO tableBO);
	/**
	 *
	 * @Description:
	 * @return List<TableBO>
	 */
	 List<TableBO> VOs2BOs(List<TableVO> vOList);
}