package com.tianhua.datafactory.controller;

import com.tianhua.datafactory.domain.enums.ApiTypeEnum;
import com.tianhua.datafactory.vo.OptionsVO;
import com.tianhua.datafactory.vo.datasource.DataSourceVO;
import com.tianhua.datafactory.vo.model.FieldVO;
import com.tianhua.datafactory.vo.model.ModelSuffixConfigVO;
import com.tianhua.datafactory.vo.model.ParamModelVO;
import com.tianhua.datafactory.vo.project.ApiVO;
import com.tianhua.datafactory.vo.project.ProjectVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
* @Description:控制层基础父类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
public class BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 包装option适配amis框架
	 * @param projectConfigVOList
	 * @return
	 */
	public OptionsVO wrapperProject(List<ProjectVO> projectConfigVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(projectConfigVOList)){
			return optionsVO;
		}
		projectConfigVOList.stream().forEach(projectConfigVO -> {
			optionsVO.addOptionItem(projectConfigVO.getProjectDesc(), projectConfigVO.getProjectCode());
		});
		return optionsVO;
	}





	/**
	 * 包装option适配amis框架
	 * @param modelSuffixConfigVOList
	 * @return
	 */
	public OptionsVO wrapperModelSuffix(List<ModelSuffixConfigVO> modelSuffixConfigVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(modelSuffixConfigVOList)){
			return optionsVO;
		}
		modelSuffixConfigVOList.stream().forEach(modelSuffixConfigVO -> {
			optionsVO.addOptionItem(modelSuffixConfigVO.getSuffix(), modelSuffixConfigVO.getSuffix());
		});
		return optionsVO;
	}



	/**
	 * 包装option适配amis框架
	 * @param dataSourceVOList
	 * @return
	 */
	public OptionsVO wrapperDataSource(List<DataSourceVO> dataSourceVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(dataSourceVOList)){
			return optionsVO;
		}
		dataSourceVOList.stream().forEach(dataSourceVO -> {
			optionsVO.addOptionItem(dataSourceVO.getSourceName(), dataSourceVO.getSourceCode());
		});
		return optionsVO;
	}


	/**
	 * 包装option适配amis框架
	 * @param paramModelVOList
	 * @return
	 */
	public OptionsVO wrapperParamModel(List<ParamModelVO> paramModelVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(paramModelVOList)){
			return optionsVO;
		}
		paramModelVOList.stream().forEach(paramModelVO -> {
			optionsVO.addOptionItem(paramModelVO.getParamClassName()+"("+paramModelVO.getParamClassDesc()+")", paramModelVO.getParamClassName());
		});
		return optionsVO;
	}

	/**
	 * 包装option适配amis框架
	 * @param fieldVOList
	 * @return
	 */
	public OptionsVO wrapperFieldModel(List<FieldVO> fieldVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(fieldVOList)){
			return optionsVO;
		}
		fieldVOList.stream().forEach(fieldVO -> {
			optionsVO.addOptionItem(fieldVO.getFieldName(), fieldVO.getFieldDesc());
		});
		return optionsVO;
	}


	/**
	 * 包装option适配amis框架
	 * @param apiVOList
	 * @return
	 */
	public OptionsVO wrapperApiModel(List<ApiVO> apiVOList){
		OptionsVO optionsVO = new OptionsVO();
		if(CollectionUtils.isEmpty(apiVOList)){
			return optionsVO;
		}
		apiVOList.stream().forEach(apiVO -> {
			optionsVO.addOptionItem(apiVO.getApiSign(), apiVO.getApiSign());
		});
		return optionsVO;
	}
}
