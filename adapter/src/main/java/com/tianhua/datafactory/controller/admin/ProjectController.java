package com.tianhua.datafactory.controller.admin;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.ProjectConverter;
import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;
import com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import com.tianhua.datafactory.domain.repository.ProjectRepository;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.project.ProjectVO;
import com.tianhua.datafactory.vo.query.ProjectQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.coderman.utils.response.ResultDataDto;


/**
* @Description:控制层
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@RestController
public class ProjectController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectRepository projectRepository;


	@Autowired
	private ProjectQueryRepository projectQueryRepository;


	/**
	 *
	 * @Description 新建项目
	 * @param projectVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/project/add")
	public ResultDataDto<Boolean> add(@RequestBody  ProjectVO projectVO){
		projectVO.init();
		projectVO.setStatus(ApiModelFieldStatusEnum.USING.getStatus());
		projectRepository.saveProject(ProjectConverter.INSTANCE.vo2bo(projectVO));
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 根据code获取项目详情
	 * @param projectCode
	 * @return Boolean
	 */
	@RequestMapping(value = "/project/getByCode/{projectCode}")
	public ResultDataDto<ProjectVO> getByCode(@PathVariable(value = "projectCode") String projectCode){
		ProjectBO projectBO = projectRepository.getByCode(projectCode);
		return ResultDataDto.success(ProjectConverter.INSTANCE.bo2VO(projectBO));
	}

	/**
	 *
	 * @Description 分页获取项目信息
	 * @param pageVO
	 * @return PageVO<ProjectVO>
	 */
	@RequestMapping(value = "/project/pagelist")
	public ResultDataDto<PageVO<ProjectVO>> getPageList(ProjectQueryVO pageVO){
		PageBean pageBean = pageVO.getPageBean();
		pageBean = projectQueryRepository.queryProjectPage(pageBean);
		logger.info("ProjectQueryVO pageVo = "+ JSON.toJSONString(pageVO));
		List<ProjectVO> moduleConfigVOList = ProjectConverter.INSTANCE.BOs2VOs(projectQueryRepository.queryProjectPage(pageBean).getRows());
		pageVO.setRows(moduleConfigVOList);
		pageVO.setCount(pageBean.getCount());
		return ResultDataDto.success(pageVO);
	}

	/**
	 *
	 * @Description 修改项目信息
	 * @param projectVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/project/update/{projectCode}",method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public ResultDataDto<Boolean> update(@PathVariable(value = "projectCode") String projectCode, @RequestBody  ProjectVO projectVO){
		projectRepository.updateProject(ProjectConverter.INSTANCE.vo2bo(projectVO));
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 搜索项目信息
	 * @param content
	 * @return List<ProjectVO>
	 */
	@RequestMapping(value = "/project/search",method = RequestMethod.GET)
	public ResultDataDto select(String content){
		List<ProjectBO> projectBOList = projectQueryRepository.searchProject(content);
		return ResultDataDto.success(wrapperProject(ProjectConverter.INSTANCE.BOs2VOs(projectBOList)));
	}
}
