package com.tianhua.datafactory.controller.admin;

import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.convert.TableConverter;
import com.tianhua.datafactory.core.service.TableModelBuilderService;
import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.bo.model.TableBO;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.vo.PageVO;
import com.tianhua.datafactory.vo.model.ColumnVO;
import com.tianhua.datafactory.vo.model.TableVO;
import com.tianhua.datafactory.vo.query.TableQueryVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class TableController extends BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(TableController.class);

	@Autowired
	private ModelRepository modelRepository;

	@Autowired
	private ModelQueryRepository modelQueryRepository;

	@Autowired
	private TableModelBuilderService tableModelBuilderService;

	/**
	 *
	 * @Description 新建表结构模型
	 * @param tableVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/table/add",method = RequestMethod.POST)
	public ResultDataDto<Boolean> add(@RequestBody TableVO tableVO){
		modelRepository.saveDBModel(TableConverter.INSTANCE.vo2bo(tableVO));
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 根据code获取表结构模型
	 * @param id
	 * @return Boolean
	 */
	@RequestMapping(value = "/table/getById/{id}")
	public ResultDataDto<TableVO> getByTableId(@PathVariable(value = "id") Long id){
		TableBO tableBO = modelQueryRepository.getByTableId(id);
		return ResultDataDto.success(TableConverter.INSTANCE.bo2VO(tableBO));
	}

	/**
	 *
	 * @Description 分页获取表结构模型
	 * @param pageVO
	 * @return PageVO<TableVO>
	 */
	@RequestMapping(value = "/table/pagelist")
	public ResultDataDto<PageVO<TableVO>> getPageList(TableQueryVO pageVO){
		PageBean pageBean = pageVO.getPageBean();
		pageBean = modelQueryRepository.queryTablePage(pageBean);
		List<TableVO> tableVOList = TableConverter.INSTANCE.BOs2VOs(modelQueryRepository.queryTablePage(pageBean).getRows());
		pageVO.setRows(tableVOList);
		pageVO.setCount(pageBean.getCount());
		return ResultDataDto.success(pageVO);
	}

	/**
	 *
	 * @Description 修改表结构模型
	 * @param id
	 * @param tableVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/table/update/{id}")
	public ResultDataDto<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody  TableVO tableVO){
		tableVO.init();
		TableBO tableBO = TableConverter.INSTANCE.vo2bo(tableVO);
		tableBO.setId(id);
		modelRepository.updateDBModel(tableBO);
		return ResultDataDto.success();
	}

	/**
	 *
	 * @Description 搜索表结构模型
	 * @param content
	 * @return List<TableVO>
	 */
	@RequestMapping(value = "/table/search")
	public ResultDataDto<List<TableVO>> select(@RequestParam(value = "content", required = true) String content){
		List<TableBO> tableBOList = modelQueryRepository.searchTable(content);
		return ResultDataDto.success(TableConverter.INSTANCE.BOs2VOs(tableBOList));
	}

	/**
	 *
	 * @Description 搜索表结构模型
	 * @param content
	 * @return List<TableVO>
	 */
	@RequestMapping(value = "/table/searchdb",method = RequestMethod.GET)
	public ResultDataDto searchdb(String content){
		if(StringUtils.isEmpty(content)){
			return ResultDataDto.success();
		}
		List<TableBO> tableBOList = modelQueryRepository.searchTable(content);
		Set<String> dbNameSet = tableBOList.stream().map(TableBO::getDbName).collect(Collectors.toSet());

		return ResultDataDto.success(wrapperDbNameList(dbNameSet));
	}



	/**
	 *
	 * @Description 根据表ID获取表字段列表
	 * @param tableId
	 * @return Boolean
	 */
	@RequestMapping(value = "/table/columns/{tableId}",method = RequestMethod.GET)
	public ResultDataDto<List<ColumnVO>> update(@PathVariable(value = "tableId") Long tableId){
		TableBO tableBO = modelQueryRepository.getByTableId(tableId);
		TableVO tableVO = TableConverter.INSTANCE.bo2VO(tableBO);
		return ResultDataDto.success(tableVO.getColumnList());
	}

	/**
	 *
	 * @Description 通过plantUML 文档 构建 表结构模型
	 * @param tableVO
	 * @return Boolean
	 */
	@RequestMapping(value = "/table/upload",method = RequestMethod.POST)
	public ResultDataDto<Boolean> build(@RequestBody TableVO tableVO){
		tableModelBuilderService.buildERModel(TableConverter.INSTANCE.vo2bo(tableVO));
		return ResultDataDto.success();
	}


}
