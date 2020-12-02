package com.coderman.tianhua.datafactory.api.controller;

import com.coderman.tianhua.datafactory.core.service.DataSourceDetailService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceDetailVO;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* @Description:数据源详情表控制层
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@RestController
@Slf4j
public class DataSourceDetailController extends BaseController{

	@Autowired
	private DataSourceDetailService dataSourceDetailService;


	/**
     * @Description:新增数据源详情表
     * @version v1.0
     * @param dataSourceDetailVo
     * @return ResultDto
     */
    @RequestMapping(value = "/dataSourceDetail/add",method = RequestMethod.POST)
    public ResultDto save(@RequestBody DataSourceDetailVO dataSourceDetailVo){
		try {
			return dataSourceDetailService.save(dataSourceDetailVo);
		} catch (Exception e) {
			log.error("保存失败",e);
			return ResultDto.setErrorCodeMsg("保存失败");
		}

    }

	/**
	 * @Description:修改数据源详情表
	 * @version v1.0
	 * @param dataSourceDetailVo
	 * @return ResultDto
	 */
    @RequestMapping(value = "/dataSourceDetail/update",method = RequestMethod.POST)
    public ResultDto update(@RequestBody DataSourceDetailVO dataSourceDetailVo){
		try {
			return dataSourceDetailService.update(dataSourceDetailVo);
		} catch (Exception e) {
			log.error("修改失败",e);
			return ResultDto.setErrorCodeMsg("修改失败");
		}
	}

	/**
	 * @Description:根据id删除数据源详情表
	 * @version v1.0
	 * @param id
	 * @return ResultDto
	 */
	@PostMapping("/dataSourceDetail/delete")
	public ResultDto delete(@RequestParam(name = "id") Long id){
		return dataSourceDetailService.delete(id);
	}

	/**
	 * @Description:根据ID获取数据源详情表单条记录
	 * @version v1.0
	 * @param id
	 * @return ResultDataDto
	 */
	@GetMapping("/dataSourceDetail/get")
	public ResultDataDto getById(@RequestParam(name = "id") Long id){
		try {
			return dataSourceDetailService.getById(id);
		} catch (Exception e) {
			log.error("获取数据失败",e);
			return ResultDataDto.setErrorCodeMsg("获取数据失败");
		}
	}

	/**
	 * @Description:分页获取数据源详情表记录
	 * @version v1.0
	 * @return ResultDataDto
	 */
	@GetMapping("/dataSourceDetail/getpage")
	public ResultDataDto getPage(){
		//todo impl code
		return new ResultDataDto();
	}

	/**
	 * @Description:修改数据源详情表状态
	 * @version v1.0
	 * @param id
     * @param status
	 * @return ResultDataDto
	 */
	@PostMapping("/dataSourceDetail/changestatus")
	public ResultDto changeStatus(@RequestParam(name = "id") Long id,@RequestParam(name = "status") int status){
		//todo impl code
		return new ResultDto();
	}

}
