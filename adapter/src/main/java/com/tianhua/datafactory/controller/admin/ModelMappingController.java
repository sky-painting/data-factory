package com.tianhua.datafactory.controller.admin;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.ModelMappingConverter;
import com.tianhua.datafactory.convert.ModelSuffixConverter;
import com.tianhua.datafactory.core.service.ModelMappingBuilderService;
import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.vo.model.ModelMappingVO;
import com.tianhua.datafactory.vo.model.ModelSuffixConfigVO;
import com.tianhua.datafactory.vo.query.ModelMappingQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
* @Description:控制层
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@RestController
public class ModelMappingController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(ModelMappingController.class);


	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private ModelQueryRepository modelQueryRepository;

	@Autowired
	private ModelMappingBuilderService modelMappingBuilderService;
	/**
	 *
	 * @Description 新建模型后缀
	 * @param modelMappingVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/modelmapping/add",method = RequestMethod.POST)
	public ResultDataDto<Boolean> add(@RequestBody ModelMappingVO modelMappingVO){
		modelRepository.saveModelMapping(ModelMappingConverter.INSTANCE.vo2bo(modelMappingVO));
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 修改模型后缀
	 * @param id
	 * @param modelSuffixConfigVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/modelmapping/update/{id}",method = RequestMethod.POST)
	public ResultDataDto<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody ModelSuffixConfigVO modelSuffixConfigVO){
		ModelSuffixConfigBO modelSuffixConfigBO = ModelSuffixConverter.INSTANCE.vo2bo(modelSuffixConfigVO);
		modelSuffixConfigBO.setId(id);
		modelRepository.updateModelSuffix(modelSuffixConfigBO);
		return ResultDataDto.success();
	}

	@GetMapping(value = "/modelmapping/pagelist")
	public ResultDataDto getList(ModelMappingQueryVO modelMappingQueryVO){
		PageBean pageBean = modelMappingQueryVO.getPageBean();
		List<ModelMappingVO> modelSuffixConfigVOList = ModelMappingConverter.INSTANCE.BOs2VOs(modelQueryRepository.queryModelMappingPage(pageBean).getRows());
		modelMappingQueryVO.setRows(modelSuffixConfigVOList);
		modelMappingQueryVO.setCount(pageBean.getCount());
		return ResultDataDto.success(modelMappingQueryVO);
	}


	@GetMapping(value = "/modelmapping/getById/{id}")
	public ResultDataDto<ModelSuffixConfigVO> getById(@PathVariable(value = "id") Long id){
		Optional<ModelSuffixConfigBO> modelSuffixConfigBOOptional = modelQueryRepository.getModelSuffixConfigList().stream().filter(modelSuffixConfigBO -> modelSuffixConfigBO.getId().equals(id)).findFirst();
		return ResultDataDto.success(ModelSuffixConverter.INSTANCE.bo2VO(modelSuffixConfigBOOptional.get()));
	}


	/**
	 * 快速构建映射模型
	 * @param modelMappingVO
	 * @return
	 */
	@PostMapping(value = "/modelmapping/fast")
	public ResultDataDto<Boolean> fastMapping(@RequestBody ModelMappingVO modelMappingVO){
		modelMappingBuilderService.buildModelMappingBatch(ModelMappingConverter.INSTANCE.vo2bo(modelMappingVO));
		return ResultDataDto.success();
	}

}
