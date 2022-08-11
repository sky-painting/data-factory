package com.tianhua.datafactory.controller.admin;

import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.FieldConverter;
import com.tianhua.datafactory.convert.ParamConverter;
import com.tianhua.datafactory.core.service.PlantUMLDomainModelBuilderService;
import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.model.FieldExtBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.model.FieldVO;
import com.tianhua.datafactory.vo.model.ParamModelVO;
import com.tianhua.datafactory.vo.query.ParamModelQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ParamController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(ParamController.class);

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private ModelQueryRepository modelQueryRepository;

	@Autowired
	private PlantUMLDomainModelBuilderService plantUMLDomainModelBuilderService;

	/**
	 *
	 * @Description 新建参数模型
	 * @param paramModelVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/parammodel/add",method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	public ResultDataDto<Boolean> add( @RequestBody ParamModelVO paramModelVO){

		ParamModelBO paramModelBO = ParamConverter.INSTANCE.vo2bo(paramModelVO);
		modelRepository.saveParamModel(paramModelBO);
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 根据code获取参数模型
	 * @param id
	 * @return Boolean
	 */
	@RequestMapping(value = "/parammodel/getById/{id}")
	public ResultDataDto<ParamModelVO> getByID(@PathVariable(value = "id") Long id){
		ParamModelBO paramModelBO = modelQueryRepository.getByParamId(id);
		return ResultDataDto.success(ParamConverter.INSTANCE.bo2VO(paramModelBO));
	}

	/**
	 *
	 * @Description 分页获取参数模型
	 * @param pageVO
	 * @return PageVO<ParamModelVO>
	 */
	@RequestMapping(value = "/parammodel/pagelist")
	public ResultDataDto<PageVO<ParamModelVO>> getPageList(ParamModelQueryVO pageVO){
		PageBean pageBean = pageVO.getPageBean();
		List<ParamModelVO> paramModelVOList = ParamConverter.INSTANCE.BOs2VOs(modelQueryRepository.queryParamPage(pageBean).getRows());
		pageVO.setRows(paramModelVOList);
		pageVO.setCount(pageBean.getCount());
		return ResultDataDto.success(pageVO);

	}

	/**
	 *
	 * @Description 修改参数模型
	 * @param id
	 * @param paramModelVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/parammodel/update/{id}")
	public ResultDataDto<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody  ParamModelVO paramModelVO){
		ParamModelBO paramModelBO = ParamConverter.INSTANCE.vo2bo(paramModelVO);
		paramModelBO.setId(id);
		paramModelBO.getFieldBeanList().forEach(fieldBO -> {
			fieldBO.setParamClassName(paramModelBO.getParamClassName());
			fieldBO.setProjectCode(paramModelBO.getProjectCode());
		});
		modelRepository.updateParamModel(paramModelBO);
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 搜索参数模型
	 * @param content
	 * @return List<ParamModelVO>
	 */
	@RequestMapping(value = "/parammodel/search")
	public ResultDataDto<List<ParamModelVO>> select(@RequestParam(value = "content", required = true) String content){
		List<ParamModelVO> paramModelVOS = ParamConverter.INSTANCE.BOs2VOs(modelQueryRepository.searchParamModel(content));
		return ResultDataDto.success(paramModelVOS);
	}



	/**
	 *
	 * @Description 搜索参数模型
	 * @param projectCode
	 * @return List<ParamModelVO>
	 */
	@RequestMapping(value = "/parammodel/getByProjectCode/{projectCode}")
	public ResultDataDto getByProjectCode(@PathVariable(value = "projectCode", required = false) String projectCode){
		List<ParamModelVO> paramModelVOS = ParamConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelByProjectCode(projectCode));
		return ResultDataDto.success(wrapperParamModel(paramModelVOS));
	}


	/**
	 *
	 * @Description 搜索参数模型
	 * @param projectCode
	 * @return List<ParamModelVO>
	 */
	@RequestMapping(value = "/parammodel/getFieldList/{projectCode}/{paramClassName}")
	public ResultDataDto getFieldList(@PathVariable(value = "projectCode", required = false) String projectCode,@PathVariable(value = "paramClassName", required = false) String paramClassName){
		List<FieldVO> fieldVOList = FieldConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelField(projectCode,paramClassName));
		return ResultDataDto.success(wrapperFieldModel(fieldVOList));
	}

	@PostMapping("/parammodel/batchBuild")
	public ResultDataDto<Boolean> batchBuild(@RequestBody ParamModelVO paramModelVO){
		plantUMLDomainModelBuilderService.initPlantUMlDomainModel(paramModelVO.getProjectCode(),paramModelVO.getFile());
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 搜索参数模型
	 * @param projectCode
	 * @return List<ParamModelVO>
	 */
	@RequestMapping(value = "/parammodel/getByProjectCodeV2")
	public ResultDataDto getByProjectCodeV2(@RequestParam(value = "projectCode", required = false) String projectCode){
		if(StringUtils.isEmpty(projectCode)){
			return ResultDataDto.success();
		}
		List<ParamModelVO> paramModelVOS = ParamConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelByProjectCode(projectCode));
		return ResultDataDto.success(wrapperParamModel(paramModelVOS));
	}

	@RequestMapping(value = "/parammodel/getFieldListV2")
	public ResultDataDto getFieldListV2(@RequestParam(value = "projectCode", required = false) String projectCode,@RequestParam(value = "paramClassName", required = false) String paramClassName){
		List<FieldVO> fieldVOList = FieldConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelField(projectCode,paramClassName));
		//适配amis service组件 使用map封装一层
		Map<String,Object> mapResult = new HashMap<>();
		mapResult.put("rows",fieldVOList);
		mapResult.put("count",fieldVOList.size());

		return ResultDataDto.success(mapResult);
	}
}
