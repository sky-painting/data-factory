package com.tianhua.datafactory.controller.admin;

import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.ApiConverter;
import com.tianhua.datafactory.core.service.ApiMockDataAdapter;
import com.tianhua.datafactory.core.service.PlantUMLApiModelBuilderService;
import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import com.tianhua.datafactory.domain.repository.ProjectRepository;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.project.ApiMockVO;
import com.tianhua.datafactory.vo.project.ApiVO;
import com.tianhua.datafactory.vo.query.ApiQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.coderman.utils.response.ResultDataDto;


/**
* @Description:控制层
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@RestController
public class ApiController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(ApiController.class);


	@Autowired
	private ProjectQueryRepository projectQueryRepository;

	@Autowired
	private ProjectRepository projectRepository;


	@Autowired
	private PlantUMLApiModelBuilderService plantUMLApiModelBuilderService;
	@Autowired
	private ApiMockDataAdapter apiMockDataAdapter;

	/**
	 *
	 * @Description 新建api信息
	 * @param apiVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/add",method = RequestMethod.POST)
	public ResultDataDto<Boolean> add(@RequestBody  ApiVO apiVO){
		ApiBO apiBO = ApiConverter.INSTANCE.vo2bo(apiVO);
		apiBO.init();
		ProjectBO projectBO = ProjectBO.getInstance();
		projectBO.addApiBo(apiBO);
		projectRepository.saveProject(projectBO);
		return ResultDataDto.success(true);
	}

	/**
	 *
	 * @Description 分页获取api信息
	 * @param apiQueryVO
	 * @return PageVO<ApiVO>
	 */
	@RequestMapping(value = "/api/pagelist")
	public ResultDataDto<PageVO<ApiVO>> getPageList(ApiQueryVO apiQueryVO){
		PageBean pageBean = apiQueryVO.getPageBean();
		pageBean = projectQueryRepository.queryApiPage(pageBean);
		List<ApiVO> apiVOList = ApiConverter.INSTANCE.BOs2VOs(projectQueryRepository.queryApiPage(pageBean).getRows());

		apiQueryVO.setRows(apiVOList);
		apiQueryVO.setCount(pageBean.getCount());
		return ResultDataDto.success(apiQueryVO);
	}

	/**
	 *
	 * @Description 修改api信息
	 * @param id
	 * @param apiVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/update/{id}")
	public ResultDataDto<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody  ApiVO apiVO){
		ApiBO apiBO = ApiConverter.INSTANCE.vo2bo(apiVO);
		apiBO.setId(id);
		ProjectBO projectBO = ProjectBO.getInstance();
		projectBO.addApiBo(apiBO);
		projectRepository.updateProject(projectBO);
		return ResultDataDto.success(true);
	}


	/**
	 *
	 * @Description 修改api信息
	 * @param id
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/disable/{id}",method = RequestMethod.POST)
	public ResultDataDto<Boolean> disable(@PathVariable(value = "id") Long id){
		ApiBO apiBO = projectQueryRepository.getApiById(id);
		apiBO.disable();
		ProjectBO projectBO = ProjectBO.getInstance();
		projectBO.addApiBo(apiBO);
		projectRepository.updateProject(projectBO);
		return ResultDataDto.success(true);
	}

	/**
	 *
	 * @Description 修改api信息
	 * @param id
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/enable/{id}",method = RequestMethod.POST)
	public ResultDataDto<Boolean> enable(@PathVariable(value = "id") Long id){
		ApiBO apiBO = projectQueryRepository.getApiById(id);
		apiBO.enable();
		ProjectBO projectBO = ProjectBO.getInstance();
		projectBO.addApiBo(apiBO);
		projectRepository.updateProject(projectBO);
		return ResultDataDto.success(true);
	}


	/**
	 *
	 * @Description 搜索api信息
	 * @param content
	 * @return List<ApiVO>
	 */
	@RequestMapping(value = "/api/search")
	public ResultDataDto<List<ApiVO>> select(@RequestParam(value = "content", required = true) String content){
		List<ApiBO> list = projectQueryRepository.searchApi(content);
		return ResultDataDto.success(ApiConverter.INSTANCE.BOs2VOs(list));
	}

	@RequestMapping(value = "/api/getById/{id}")
	public ResultDataDto<ApiVO> getById(@PathVariable(value = "id") Long id){
		ApiBO apiBO = projectQueryRepository.getApiById(id);
		return ResultDataDto.success(ApiConverter.INSTANCE.bo2VO(apiBO));
	}


	/**
	 * 以文件上传的方式批量构建api模型
	 * @param apiVO
	 * @return
	 */
	@PostMapping("/api/batchBuild")
	public ResultDataDto<Boolean> batchBuild(@RequestBody ApiVO apiVO){
		plantUMLApiModelBuilderService.initPlantUMlModel(apiVO.getProjectCode(), apiVO.getFile());
		return ResultDataDto.success();
	}

	/**
	 * 根据项目编码查询apiSign
	 * @param projectCode
	 * @return
	 */
	@GetMapping("/api/searchApiSign")
	public ResultDataDto batchBuild(@RequestParam(value = "projectCode", required = false) String projectCode){
		if(StringUtils.isEmpty(projectCode)){
			return ResultDataDto.success();
		}
		List<ApiBO> list = projectQueryRepository.getApiListByCode(projectCode);
		return ResultDataDto.success(wrapperApiModel(ApiConverter.INSTANCE.BOs2VOs(list)));
	}


	/**
	 *
	 * @Description mock api接口返回值数据
	 * @param apiMockVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/reqmock",method = RequestMethod.POST)
	public ResultDataDto reqMock(@RequestBody ApiMockVO apiMockVO) throws Exception {
		Object value = apiMockDataAdapter.getApiMockData(apiMockVO.getApiSign(), apiMockVO.getSuccessData());
		return ResultDataDto.success(value);
	}



}
