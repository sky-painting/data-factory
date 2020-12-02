package com.lightsnail.tianhua.datafactory.core.service.impl;

import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.lightsnail.tianhua.datafactory.core.entity.DataSourceDetailEntity;
import com.lightsnail.tianhua.datafactory.core.mapper.DataSourceDetailMapper;
import com.lightsnail.tianhua.datafactory.core.service.DataSourceDetailService;
import com.lightsnail.tianhua.datafactory.core.vo.DataSourceDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description:数据源详情表Service接口实现类
 * @Author：fanchunshuai
 * @CreateTime：2020-12-02 23:07:13
 * @version v1.0
 */
@Service
@Slf4j
public class DataSourceDetailServiceImpl implements DataSourceDetailService {


	@Resource
	private DataSourceDetailMapper dataSourceDetailMapper;

    @Autowired
    private CglibConvertService cglibConvertService;

	@Override
	public ResultDto save(DataSourceDetailVO dataSourceDetailVo)  throws Exception{
		ResultDto resultDto = new ResultDto();
        DataSourceDetailEntity dataSourceDetailEntity = cglibConvertService.copyPropertity(DataSourceDetailEntity.class,dataSourceDetailVo);
        dataSourceDetailMapper.insert(dataSourceDetailEntity);
		return resultDto;
	}
	
	@Override
	public ResultDto delete(Long id) {
		ResultDto resultDto = new ResultDto();
		log.info("dataSourceDetailService.delete,id="+id);
       dataSourceDetailMapper.deleteById(id);
		return resultDto;
	}
	
	@Override
	public ResultDataDto<DataSourceDetailVO> getById(Long id)  throws Exception {
		ResultDataDto<DataSourceDetailVO> resultDataDto = new ResultDataDto<DataSourceDetailVO>();
		DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getById(id);
		DataSourceDetailVO dataSourceDetailVo = cglibConvertService.copyPropertity(DataSourceDetailVO.class,dataSourceDetailEntity);
		resultDataDto.setData(dataSourceDetailVo);
		return resultDataDto;
	}
	
	@Override
	public ResultDataDto<List<DataSourceDetailVO>> getAll()  throws Exception {
		ResultDataDto<List<DataSourceDetailVO>> resultDataDto = new ResultDataDto<List<DataSourceDetailVO>>();
		//todo impl code
		return	resultDataDto;
	}

	@Override
	public ResultDto update(DataSourceDetailVO dataSourceDetailVo)  throws Exception {
		ResultDto resultDto = new ResultDto();
		DataSourceDetailEntity dataSourceDetailEntity = cglibConvertService.copyPropertity(DataSourceDetailEntity.class,dataSourceDetailVo);
		dataSourceDetailMapper.update(dataSourceDetailEntity);
		return resultDto;
	}

}