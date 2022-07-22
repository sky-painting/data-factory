package com.tianhua.datafactory.convert;

import java.util.List;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;

import com.tianhua.datafactory.vo.project.ProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
* @Description:projectConvertervobo接口
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@Mapper
public interface ProjectConverter{
	ProjectConverter INSTANCE = Mappers.getMapper(ProjectConverter.class);

	/**
	 *
	 * @Description:
	 * @return List<ProjectVO>
	 */
	 List<ProjectVO> BOs2VOs(List<ProjectBO> projectBOList);
	/**
	 *
	 * @Description:
	 * @return ProjectVO
	 */
	 ProjectVO bo2VO(ProjectBO projectBO);
	/**
	 *
	 * @Description:
	 * @return ProjectBO
	 */
	 ProjectBO vo2bo(ProjectVO projectVO);
	/**
	 *
	 * @Description:
	 * @return List<ProjectBO>
	 */
	 List<ProjectBO> VOs2BOs(List<ProjectVO> vOList);
}