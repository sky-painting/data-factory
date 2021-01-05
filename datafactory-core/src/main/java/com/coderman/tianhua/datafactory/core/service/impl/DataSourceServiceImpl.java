package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.coderman.tianhua.datafactory.core.constants.InnerDataSourceCode;
import com.coderman.tianhua.datafactory.core.entity.DataSourceDetailEntity;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceDetailMapper;
import com.coderman.tianhua.datafactory.core.service.ConfigServiceWrapper;
import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.coderman.tianhua.datafactory.core.entity.DataSourceEntity;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceMapper;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
	private ConfigServiceWrapper configServiceWrapper;


	@Override
	@Transactional
	public ResultDto save(DataSourceVO dataSourceVo)  throws Exception{

		if(dataSourceVo.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()){
			dataSourceVo.setSourceCode(dataSourceVo.getDataId()+"."+dataSourceVo.getGroupId());
		}
		//check
		DataSourceEntity oldEntity = dataSourceMapper.getBySourceCode(dataSourceVo.getSourceCode());
		if(oldEntity != null){
			return ResultDto.setErrorCodeMsg("sourceCode重复!");
		}

		ResultDto resultDto = new ResultDto();
        DataSourceEntity dataSourceEntity = cglibConvertService.copyPropertity(DataSourceEntity.class,dataSourceVo);
        //本地缓存
        if(dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()){
        	//存储--枚举类型
			if(dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
					|| dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode() ){
				int id = dataSourceMapper.insert(dataSourceEntity);
				DataSourceDetailEntity dataSourceDetailEntity = new DataSourceDetailEntity();
				dataSourceDetailEntity.setDataContentJson(dataSourceVo.getDataContentJson());
				dataSourceDetailEntity.setDataSourceId((long)id);
				dataSourceDetailMapper.insert(dataSourceDetailEntity);
				return resultDto;
			}
		}

        if(dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()){
			dataSourceMapper.insert(dataSourceEntity);
		}
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
		if(dataSourceEntity == null){
			return resultDataDto.setInvokeErrorMsg("查询数据为空！");
		}
		DataSourceVO dataSourceVo = cglibConvertService.copyPropertity(DataSourceVO.class,dataSourceEntity);
		if(dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()){
			//存储--枚举类型
			if(dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
					|| dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode() ) {
				DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getByDataSourceId(dataSourceEntity.getId());
				dataSourceVo.setDataContentJson(dataSourceDetailEntity.getDataContentJson());
			}
		}

		if(dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()){
			String sourceCode = dataSourceEntity.getSourceCode();
			String groupId = sourceCode.substring(sourceCode.lastIndexOf(".")+1);
			String dataId = sourceCode.substring(0,sourceCode.lastIndexOf("."));
			//todo 自动适配解析k,v ?
			String content = configServiceWrapper.getConfig(dataId,groupId);
			dataSourceVo.setDataContentJson(content);
		}

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
	public ResultDataDto<String> getDataSourceDetail(String dataSourceCode) throws Exception {

		DataSourceEntity dataSourceEntity = dataSourceMapper.getBySourceCode(dataSourceCode);
		if(dataSourceEntity == null){
			return ResultDataDto.setErrorCodeMsg("查询数据为空!");
		}

		//本地缓存
		if(dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()){
			DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getByDataSourceId(dataSourceEntity.getId());
			if(dataSourceDetailEntity == null || StringUtils.isEmpty(dataSourceDetailEntity.getDataContentJson())){
				return ResultDataDto.setErrorCodeMsg("查询数据为空!");
			}
			ResultDataDto resultDataDto = new ResultDataDto();
			return resultDataDto.setData(dataSourceDetailEntity.getDataContentJson());
		}
		//动态获取
		else {

		}

		//内置数据源

		return ResultDataDto.setErrorCodeMsg("查询数据为空!");
	}

}