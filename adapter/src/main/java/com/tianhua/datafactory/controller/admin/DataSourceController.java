package com.tianhua.datafactory.controller.admin;

import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.DataSourceConverter;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.enums.VisitStrategyEnums;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.repository.DataSourceRepository;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.datasource.DataSourceVO;
import com.tianhua.datafactory.vo.query.DataSourceQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* @Description:数据源管理表控制层
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@RestController
@Slf4j
public class DataSourceController extends BaseController {

	@Autowired
	private DataSourceRepository dataSourceRepository;


	@Autowired
	private DataSourceQueryRepository dataSourceQueryRepository;

	/**
     * @Description:新增数据源管理表
     * @version v1.0
     * @param dataSourceVo
     * @return ResultDto
     */
    @RequestMapping(value = "/datasource/regist",method = RequestMethod.POST)
    public ResultDto save(@RequestBody DataSourceVO dataSourceVo){
		try {
			dataSourceRepository.regist(DataSourceConverter.INSTANCE.vo2bo(dataSourceVo));
			return ResultDto.success();
		} catch (Exception e) {
			log.error("保存失败",e);
			return ResultDto.fail("500","保存失败");
		}

    }

	/**
	 * @Description:修改数据源管理表
	 * @version v1.0
	 * @param dataSourceVo
	 * @return ResultDto
	 */
    @RequestMapping(value = "/datasource/update/{id}",method = RequestMethod.POST)
    public ResultDto update(@PathVariable(value = "id") Long id, @RequestBody DataSourceVO dataSourceVo){
		try {
			dataSourceVo.init();
			dataSourceVo.setId(id);
			dataSourceRepository.update(DataSourceConverter.INSTANCE.vo2bo(dataSourceVo));
			return ResultDto.success();
		} catch (Exception e) {
			log.error("修改失败",e);
			return ResultDto.fail("500","修改失败");
		}
	}

	/**
	 * @Description:根据id删除数据源管理表
	 * @version v1.0
	 * @param id
	 * @return ResultDto
	 */
	@PostMapping(value = "/datasource/delete/{id}")
	public ResultDto delete(@PathVariable(value = "id") Long id){
		dataSourceRepository.delete(id);
		 return  ResultDto.success();
	}

	/**
	 * @Description:根据ID获取数据源管理表单条记录
	 * @version v1.0
	 * @param id
	 * @return ResultDataDto
	 */
	@GetMapping("/datasource/getById/{id}")
	public ResultDataDto<DataSourceVO> getById(@PathVariable(value = "id") Long id){
		try {
			DataSourceVO dataSourceVO = DataSourceConverter.INSTANCE.bo2VO(dataSourceRepository.getById(id));
			return  ResultDataDto.success(dataSourceVO);
		} catch (Exception e) {
			log.error("查询失败",e);
			return ResultDataDto.fail("500","查询失败");
		}
	}

	/**
	 * @Description:分页获取数据源管理表记录
	 * @version v1.0
	 * @return ResultDataDto
	 */
	@GetMapping("/datasource/pagelist")
	public ResultDataDto<PageVO<DataSourceVO>> getPage(DataSourceQueryVO pageVO){
		PageBean pageBean = pageVO.getPageBean();
		List<DataSourceVO> dataSourceVOList = DataSourceConverter.INSTANCE.bo2VOs(dataSourceRepository.getPageList(pageBean).getRows());
		dataSourceVOList.stream().forEach(dataSourceVO -> {
			dataSourceVO.setSourceTypeDesc(DataSourceTypeEnum.getDesc(dataSourceVO.getSourceType()));
			dataSourceVO.setVisitStrategyDesc(VisitStrategyEnums.getDesc(dataSourceVO.getVisitStrategy()));
		});
		pageVO.setRows(dataSourceVOList);
		pageVO.setCount(pageBean.getCount());
		return ResultDataDto.success(pageVO);
	}


	/**
	 * @Description:启用数据源
	 * @version v1.0
	 * @param id
	 * @return ResultDataDto
	 */
	@PostMapping("/datasource/enable/{id}")
	public ResultDto enable(@PathVariable(name = "id") Long id){
		 dataSourceRepository.updateStatus(id,1);
		 return ResultDto.success();
	}

	/**
	 * @Description:启用数据源
	 * @version v1.0
	 * @param id
	 * @return ResultDataDto
	 */
	@PostMapping("/datasource/disable/{id}")
	public ResultDto disable(@PathVariable(name = "id") Long id){
		dataSourceRepository.updateStatus(id,0);
		return ResultDto.success();
	}

	@RequestMapping(value = "/datasource/search",method = RequestMethod.GET)
	public ResultDataDto search(String content){
		List<DataSourceVO> dataSourceVOList = DataSourceConverter.INSTANCE.bo2VOs(dataSourceQueryRepository.search(content));
		return ResultDataDto.success(wrapperDataSource(dataSourceVOList));
	}

}
