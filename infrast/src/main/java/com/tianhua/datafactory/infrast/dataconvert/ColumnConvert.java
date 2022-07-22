package com.tianhua.datafactory.infrast.dataconvert;

import java.util.List;
import com.tianhua.datafactory.infrast.dao.dataobject.ColumnModelDO;
import com.tianhua.datafactory.domain.bo.model.ColumnBO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:数据库column模型信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ColumnConvert{
	ColumnConvert INSTANCE = Mappers.getMapper(ColumnConvert.class);

	/**
	 *
	 * @Description:
	 * @return ColumnBO
	 */
	 ColumnBO do2bo(ColumnModelDO columnModelDO);
	/**
	 *
	 * @Description:
	 * @return List<ColumnBO>
	 */
	 List<ColumnBO> doList2boList(List<ColumnModelDO> columnModelDOList);
	/**
	 *
	 * @Description:
	 * @return ColumnModelDO
	 */
	 ColumnModelDO bo2do(ColumnBO columnBO);
	/**
	 *
	 * @Description:
	 * @return List<ColumnModelDO>
	 */
	 List<ColumnModelDO> boList2doList(List<ColumnBO> columnBOList);
}