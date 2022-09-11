package com.tianhua.datafactory.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.ApiConverter;
import com.tianhua.datafactory.convert.ParamConverter;
import com.tianhua.datafactory.core.service.ApiMockDataAdapter;
import com.tianhua.datafactory.core.service.PlantUMLApiModelBuilderService;
import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.bo.datafactory.ApiMockBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import com.tianhua.datafactory.domain.repository.ProjectRepository;
import com.tianhua.datafactory.infrast.dataconvert.ApiConvert;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.StatusChangeVO;
import com.tianhua.datafactory.vo.project.ApiMockVO;
import com.tianhua.datafactory.vo.project.ApiVO;
import com.tianhua.datafactory.vo.query.ApiQueryVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ParamModelBO returnParamModel = new ParamModelBO();
		returnParamModel.setParamClassName(apiVO.getReturnParamClass());
		apiBO.setReturnParamModel(returnParamModel);
		apiBO.setReturnValue("");
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
	 * @Description 修改api状态
	 * amis bug, 迷之不好实现
	 * @param id
	 * @return Boolean
	 */
	@Deprecated
	@RequestMapping(value = "/api/status/{id}",method = RequestMethod.POST)
	public ResultDataDto<Boolean> updateStatus(@PathVariable(value = "id") Long id,@RequestBody StatusChangeVO statusChangeVO){
		logger.info("id = {}",id);
		logger.info("statusChangeVO = {}",JSON.toJSONString(statusChangeVO));
		ApiBO apiBO = projectQueryRepository.getApiById(id);

		apiBO.updateStatus(statusChangeVO.getStatus());
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
	public ResultDataDto searchApiSign(@RequestParam(value = "projectCode", required = false) String projectCode){
		if(StringUtils.isEmpty(projectCode)){
			return ResultDataDto.success();
		}
		List<ApiBO> list = projectQueryRepository.getApiListByCode(projectCode);
		return ResultDataDto.success(wrapperApiModel(ApiConverter.INSTANCE.BOs2VOs(list)));
	}



	/**
	 * 根据项目编码查询apiSign
	 * @param projectCode
	 * @return
	 */
	@GetMapping("/api/searchApiSignV2")
	public ResultDataDto searchApiSignV2(@RequestParam(value = "projectCode", required = false) String projectCode){
		if(StringUtils.isEmpty(projectCode)){
			return ResultDataDto.success();
		}
		List<ApiBO> list = projectQueryRepository.getApiListByCode(projectCode);

		return ResultDataDto.success(wrapperApiModelV2(ApiConverter.INSTANCE.BOs2VOs(list)));
	}


	/**
	 *
	 * @Description mock api接口出参mock
	 * @param apiMockVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/respmock",method = RequestMethod.POST)
	public ResultDataDto reqMock(@RequestBody ApiMockVO apiMockVO) throws Exception {
		if(StringUtils.isNotEmpty(apiMockVO.getApiMethod())){
			apiMockVO.setApiSign(apiMockVO.getApiMethod());
		}
		if(apiMockVO.getMockCount() != null && apiMockVO.getMockCount() > 1000){
			throw new Exception("web请求mock数量不可超过1000");
		}
		Object value = apiMockDataAdapter.getApiMockDataResp(ApiConverter.INSTANCE.vo2BOMock(apiMockVO));
		String jsonValue = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullBooleanAsFalse);
		apiMockVO.setMockResultData(jsonValue);
		return ResultDataDto.success(apiMockVO);
	}

	/**
	 *
	 * @Description mock api接口入参mock
	 * @param apiMockVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/api/reqmock",method = RequestMethod.POST)
	public ResultDataDto respMock(@RequestBody ApiMockVO apiMockVO) throws Exception {
		if(StringUtils.isNotEmpty(apiMockVO.getApiMethod())){
			apiMockVO.setApiSign(apiMockVO.getApiMethod());
		}
		if(apiMockVO.getMockCount() != null && apiMockVO.getMockCount() > 1000){
			throw new Exception("web请求mock数量不可超过1000");
		}
		Object value = apiMockDataAdapter.getApiMockDataReq(ApiConverter.INSTANCE.vo2BOMock(apiMockVO));
		String jsonValue = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullBooleanAsFalse);
		apiMockVO.setMockResultData(jsonValue);
		return ResultDataDto.success(apiMockVO);
	}

	@RequestMapping(value = "/api/getparamlist")
	public ResultDataDto getFieldListV2(@RequestParam(value = "apiSign", required = false) String apiSign){

		Map<String,Object> mapResult = new HashMap<>();
		mapResult.put("rows", null);
		mapResult.put("count", 0);
		if(StringUtils.isEmpty(apiSign)){
			return ResultDataDto.success(mapResult);
		}

		ApiBO apiBO = projectQueryRepository.getBySign(apiSign);

		if(CollectionUtils.isEmpty(apiBO.getParamList())){
			return ResultDataDto.success(mapResult);
		}

		ApiVO apiVO = ApiConverter.INSTANCE.bo2VO(apiBO);

		//适配amis service组件 使用map封装一层
		mapResult.put("rows", apiVO.getParamList());
		mapResult.put("count",apiVO.getParamList().size());

		return ResultDataDto.success(mapResult);
	}




}
