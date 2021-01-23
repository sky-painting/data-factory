package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.coderman.tianhua.datafactory.core.entity.DataSourceDetailEntity;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceDetailMapper;
import com.coderman.tianhua.datafactory.core.service.ConfigServiceWrapper;
import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.kvpair.KVPair;
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
 * @version v1.0
 * @Description:数据源管理表Service接口实现类
 * @Author：fanchunshuai
 * @CreateTime：2020-12-02 23:07:13
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
    @Transactional(rollbackFor = Exception.class)
    public ResultDto save(DataSourceVO dataSourceVo) throws Exception {

        if (dataSourceVo.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
            dataSourceVo.setSourceCode(dataSourceVo.getDataId() + "." + dataSourceVo.getGroupId());
        }
        //check
        DataSourceEntity oldEntity = dataSourceMapper.getBySourceCode(dataSourceVo.getSourceCode());
        if (oldEntity != null) {
            return ResultDto.setParamErrorMsg("sourceCode重复!");
        }

        ResultDto resultDto = new ResultDto();
        DataSourceEntity dataSourceEntity = cglibConvertService.copyPropertity(DataSourceEntity.class, dataSourceVo);
        //本地缓存
        if (dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            //存储--枚举类型
            if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
                    || dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode()) {
                dataSourceMapper.insert(dataSourceEntity);
                DataSourceDetailEntity dataSourceDetailEntity = new DataSourceDetailEntity();
                dataSourceDetailEntity.setDataContentJson(dataSourceVo.getDataContentJson());
                dataSourceDetailEntity.setDataSourceId(dataSourceEntity.getId());
                dataSourceDetailMapper.insert(dataSourceDetailEntity);
                return resultDto;
            }
        }

        if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
            dataSourceMapper.insert(dataSourceEntity);
        }
        return resultDto;
    }

    @Override
    public ResultDto delete(Long id) {
        ResultDto resultDto = new ResultDto();
        log.info("dataSourceService.delete,id=" + id);
        dataSourceMapper.deleteById(id);
        return resultDto;
    }

    @Override
    public ResultDataDto<DataSourceVO> getById(Long id) throws Exception {
        ResultDataDto<DataSourceVO> resultDataDto = new ResultDataDto<DataSourceVO>();
        DataSourceEntity dataSourceEntity = dataSourceMapper.getById(id);
        if (dataSourceEntity == null) {
            return resultDataDto.setInvokeErrorMsg("查询数据为空！");
        }
        DataSourceVO dataSourceVo = cglibConvertService.copyPropertity(DataSourceVO.class, dataSourceEntity);
        if (dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            //存储--枚举类型
            if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
                    || dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode()) {
                DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getByDataSourceId(dataSourceEntity.getId());
                dataSourceVo.setDataContentJson(dataSourceDetailEntity.getDataContentJson());
            }
        }

        if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
            String sourceCode = dataSourceEntity.getSourceCode();
            String groupId = sourceCode.substring(sourceCode.lastIndexOf(".") + 1);
            String dataId = sourceCode.substring(0, sourceCode.lastIndexOf("."));
            //todo 自动适配解析k,v
            String content = configServiceWrapper.getConfig(dataId, groupId);
            dataSourceVo.setDataContentJson(content);
        }

        resultDataDto.setData(dataSourceVo);
        return resultDataDto;
    }

    @Override
    public ResultDataDto<List<DataSourceVO>> getAll() throws Exception {
        ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto<List<DataSourceVO>>();
        //todo impl code
        return resultDataDto;
    }

    @Override
    public ResultDto update(DataSourceVO dataSourceVo) throws Exception {
        ResultDto resultDto = new ResultDto();
        DataSourceEntity dataSourceEntity = cglibConvertService.copyPropertity(DataSourceEntity.class, dataSourceVo);
        dataSourceMapper.update(dataSourceEntity);
        return resultDto;
    }

    @Override
    public ResultDataDto<String> getDataSourceDetail(String dataSourceCode) throws Exception {

        DataSourceEntity dataSourceEntity = dataSourceMapper.getBySourceCode(dataSourceCode);
        if (dataSourceEntity == null) {
            return ResultDataDto.setNullErrorMsg("查询数据为空!");
        }

        //本地持久化
        if (dataSourceEntity.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            DataSourceDetailEntity dataSourceDetailEntity = dataSourceDetailMapper.getByDataSourceId(dataSourceEntity.getId());
            if (dataSourceDetailEntity == null || StringUtils.isEmpty(dataSourceDetailEntity.getDataContentJson())) {
                return ResultDataDto.setNullErrorMsg("查询数据为空!");
            }
            ResultDataDto resultDataDto = new ResultDataDto();
            return resultDataDto.setData(dataSourceDetailEntity.getDataContentJson());
        }
        //todo 远程动态获取---springboot http协议优先支持 dubbo泛化调用支持tcp协议
        //这里配置的数据源默认时全量，如果需要参数则需要DataFactoryRequestFieldRuleBean2定义远程访问接口的参数和value进行动态获取，暂时先不支持
        //这里先支持全量数据的动态获取，默认进行缓存

        //todo 2.service api对接
        else {
            //nacos数据源
            if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
                String groupId = dataSourceCode.substring(dataSourceCode.lastIndexOf(".") + 1);
                String dataId = dataSourceCode.substring(0, dataSourceCode.lastIndexOf("."));
                List<KVPair<String, String>> list = configServiceWrapper.getConfigList(dataId, groupId);
                ResultDataDto resultDataDto = new ResultDataDto();
                return resultDataDto.setData(JSON.toJSONString(list));
            } else if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_SERVICE_API.getCode()) {

            }
        }

        return ResultDataDto.setNullErrorMsg("查询数据为空!");
    }

}