package com.coderman.tianhua.datafactory.core.service.impl;

import com.coderman.tianhua.datafactory.core.entity.DataSourceDetailEntity;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceDetailMapper;
import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.coderman.tianhua.datafactory.core.entity.DataSourceEntity;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceMapper;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description:数据源管理表Service接口实现类
 * @Author：fanchunshuai
 * @CreateTime：2020-12-02 23:07:13
 * @version v1.0
 */
@Service
@Slf4j
public class DataSourceServiceImpl implements DataSourceService {


	@Resource
	private DataSourceMapper dataSourceMapper;

    @Autowired
    private CglibConvertService cglibConvertService;

    @Resource
    private DataSourceDetailMapper dataSourceDetailMapper;

	@Override
	public ResultDto save(DataSourceVO dataSourceVo)  throws Exception{
		ResultDto resultDto = new ResultDto();
        DataSourceEntity dataSourceEntity = cglibConvertService.copyPropertity(DataSourceEntity.class,dataSourceVo);
        dataSourceMapper.insert(dataSourceEntity);
		return resultDto;
	}
	
	@Override
	public ResultDto delete(Long id) {
		ResultDto resultDto = new ResultDto();
		log.info("dataSourceService.delete,id="+id);
       dataSourceMapper.deleteById(id);
		return resultDto;
	}
	
	@Override
	public ResultDataDto<DataSourceVO> getById(Long id)  throws Exception {
		ResultDataDto<DataSourceVO> resultDataDto = new ResultDataDto<DataSourceVO>();
		DataSourceEntity dataSourceEntity = dataSourceMapper.getById(id);
		DataSourceVO dataSourceVo = cglibConvertService.copyPropertity(DataSourceVO.class,dataSourceEntity);
		resultDataDto.setData(dataSourceVo);
		return resultDataDto;
	}
	
	@Override
	public ResultDataDto<List<DataSourceVO>> getAll()  throws Exception {
		ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto<List<DataSourceVO>>();
		//todo impl code
		return	resultDataDto;
	}

	@Override
	public ResultDto update(DataSourceVO dataSourceVo)  throws Exception {
		ResultDto resultDto = new ResultDto();
		DataSourceEntity dataSourceEntity = cglibConvertService.copyPropertity(DataSourceEntity.class,dataSourceVo);
		dataSourceMapper.update(dataSourceEntity);
		return resultDto;
	}

	@Override
	public ResultDataDto getDataSourceDetail(String dataSourceCode) throws Exception {
		DataSourceEntity dataSourceEntity = dataSourceMapper.getBySourceCode(dataSourceCode);
		if(dataSourceEntity == null){
			return ResultDataDto.setErrorCodeMsg("查询数据为空!");
		}

		DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getByDataSourceId(dataSourceEntity.getId());
		if(dataSourceDetailEntity == null){
			return ResultDataDto.setErrorCodeMsg("查询数据为空!");
		}

		//if(dataSourceDetailMapper)

		return null;
	}

}