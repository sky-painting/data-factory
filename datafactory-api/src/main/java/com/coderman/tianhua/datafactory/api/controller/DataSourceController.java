package com.coderman.tianhua.datafactory.api.controller;

import com.coderman.tianhua.datafactory.core.bean.DataSourceQueryDTO;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* @Description:数据源管理表控制层
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
@RestController
@Slf4j
public class DataSourceController extends BaseController{

	@Autowired
	private DataSourceService dataSourceService;


	/**
     * @Description:新增数据源管理表
     * @version v1.0
     * @param dataSourceVo
     * @return ResultDto
     */
    @RequestMapping(value = "/data/source/regist",method = RequestMethod.POST)
    public ResultDto save(@RequestBody DataSourceVO dataSourceVo){
		try {
			return dataSourceService.save(dataSourceVo);
		} catch (Exception e) {
			log.error("保存失败",e);
			return ResultDto.setErrorCodeMsg("保存失败");
		}

    }

	/**
	 * @Description:修改数据源管理表
	 * @version v1.0
	 * @param dataSourceVo
	 * @return ResultDto
	 */
    @RequestMapping(value = "/data/source/update",method = RequestMethod.POST)
    public ResultDto update(@RequestBody DataSourceVO dataSourceVo){
		try {
			return dataSourceService.update(dataSourceVo);
		} catch (Exception e) {
			log.error("修改失败",e);
			return ResultDto.setErrorCodeMsg("修改失败");
		}
	}

	/**
	 * @Description:根据id删除数据源管理表
	 * @version v1.0
	 * @param id
	 * @return ResultDto
	 */
	@PostMapping("/dataSource/delete")
	public ResultDto delete(@RequestParam(name = "id") Long id){
		return dataSourceService.delete(id);
	}

	/**
	 * @Description:根据ID获取数据源管理表单条记录
	 * @version v1.0
	 * @param id
	 * @return ResultDataDto
	 */
	@GetMapping("/data/source/get")
	public ResultDataDto getById(@RequestParam(name = "id") Long id){
		try {
			return dataSourceService.getById(id);
		} catch (Exception e) {
			log.error("获取数据失败",e);
			return ResultDataDto.setErrorCodeMsg("获取数据失败");
		}
	}

	/**
	 * @Description:分页获取数据源管理表记录
	 * @version v1.0
	 * @return ResultDataDto
	 */
	@GetMapping("/dataSource/getpage")
	public ResultDataDto getPage(DataSourceQueryDTO dataSourceQueryDTO){
		//todo impl code
		return new ResultDataDto();
	}

	/**
	 * @Description:修改数据源管理表状态
	 * @version v1.0
	 * @param id
     * @param status
	 * @return ResultDataDto
	 */
	@PostMapping("/data/source/changestatus")
	public ResultDto changeStatus(@RequestParam(name = "id") Long id,@RequestParam(name = "status") int status){
		return dataSourceService.updateStatus(id,status);
	}

}
