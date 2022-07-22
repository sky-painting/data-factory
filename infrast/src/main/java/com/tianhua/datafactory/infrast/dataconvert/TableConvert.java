package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.infrast.dao.dataobject.TableModelDO;
import com.tianhua.datafactory.domain.bo.model.TableBO;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:数据库table模型信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface TableConvert{
	TableConvert INSTANCE = Mappers.getMapper(TableConvert.class);

	/**
	 *
	 * @Description:
	 * @return TableBO
	 */
	 TableBO do2bo(TableModelDO tableModelDO);
	/**
	 *
	 * @Description:
	 * @return List<TableBO>
	 */
	 List<TableBO> doList2boList(List<TableModelDO> tableModelDOList);
	/**
	 *
	 * @Description:
	 * @return TableModelDO
	 */
	 TableModelDO bo2do(TableBO tableBO);
	/**
	 *
	 * @Description:
	 * @return List<TableModelDO>
	 */
	 List<TableModelDO> boList2doList(List<TableBO> tableBOList);
}