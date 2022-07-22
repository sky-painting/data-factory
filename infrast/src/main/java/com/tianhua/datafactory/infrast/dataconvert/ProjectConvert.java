package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.infrast.dao.dataobject.ProjectConfigDO;
import java.util.List;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:项目基本信息接口
* @Author:
* @CreateTime:2022-05-27 18:08:06
* @version v1.0
*/
@Mapper
public interface ProjectConvert{
	ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

	/**
	 *
	 * @Description:
	 * @return ProjectBO
	 */
	 ProjectBO do2bo(ProjectConfigDO projectConfigDO);
	/**
	 *
	 * @Description:
	 * @return List<ProjectBO>
	 */
	 List<ProjectBO> doList2boList(List<ProjectConfigDO> projectConfigDOList);
	/**
	 *
	 * @Description:
	 * @return ProjectConfigDO
	 */
	 ProjectConfigDO bo2do(ProjectBO projectBO);
	/**
	 *
	 * @Description:
	 * @return List<ProjectConfigDO>
	 */
	 List<ProjectConfigDO> boList2doList(List<ProjectBO> projectBOList);
}