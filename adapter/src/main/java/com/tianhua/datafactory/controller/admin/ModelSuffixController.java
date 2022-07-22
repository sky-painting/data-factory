package com.tianhua.datafactory.controller.admin;

import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.ModelSuffixConverter;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.vo.model.ModelSuffixConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.coderman.utils.response.ResultDataDto;
import java.util.List;
import java.util.Optional;


/**
* @Description:控制层
* @Author:
* @CreateTime:2022-05-27 17:45:38
* @version v1.0
*/
@RestController
public class ModelSuffixController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(ModelSuffixController.class);


	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private ModelQueryRepository modelQueryRepository;

	/**
	 *
	 * @Description 新建模型后缀
	 * @param modelSuffixConfigVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/modelsuffix/add",method = RequestMethod.POST)
	public ResultDataDto<Boolean> add(@RequestBody  ModelSuffixConfigVO modelSuffixConfigVO){
		modelRepository.saveModelSuffix(ModelSuffixConverter.INSTANCE.vo2bo(modelSuffixConfigVO));
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 修改模型后缀
	 * @param id
	 * @param modelSuffixConfigVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/modelsuffix/update/{id}",method = RequestMethod.POST)
	public ResultDataDto<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody ModelSuffixConfigVO modelSuffixConfigVO){
		ModelSuffixConfigBO modelSuffixConfigBO = ModelSuffixConverter.INSTANCE.vo2bo(modelSuffixConfigVO);
		modelSuffixConfigBO.setId(id);
		modelRepository.updateModelSuffix(modelSuffixConfigBO);
		return ResultDataDto.success();
	}

	@GetMapping(value = "/modelsuffix/list")
	public ResultDataDto<List<ModelSuffixConfigVO>> getList(){
		List<ModelSuffixConfigVO> modelSuffixConfigVOList = ModelSuffixConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelSuffixConfigList());
		return ResultDataDto.success(modelSuffixConfigVOList);
	}


	@GetMapping(value = "/modelsuffix/getById/{id}")
	public ResultDataDto<ModelSuffixConfigVO> getById(@PathVariable(value = "id") Long id){
		Optional<ModelSuffixConfigBO> modelSuffixConfigBOOptional = modelQueryRepository.getModelSuffixConfigList().stream().filter(modelSuffixConfigBO -> modelSuffixConfigBO.getId().equals(id)).findFirst();
		return ResultDataDto.success(ModelSuffixConverter.INSTANCE.bo2VO(modelSuffixConfigBOOptional.get()));
	}


	@GetMapping(value = "/modelsuffix/listoption")
	public ResultDataDto getListOption(){
		List<ModelSuffixConfigVO> modelSuffixConfigVOList = ModelSuffixConverter.INSTANCE.BOs2VOs(modelQueryRepository.getModelSuffixConfigList());
		return ResultDataDto.success(wrapperModelSuffix(modelSuffixConfigVOList));
	}
}
