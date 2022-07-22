/*
package com.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.tianhua.datafactory.core.service.ConfigServiceWrapper;
import com.tianhua.datafactory.core.service.DataSourceService;
import com.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.domain.bo.PageBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDetailDO;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceDetailMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


*/
/**
 * @version v1.0
 * @Description:数据源管理表Service接口实现类
 * @Author：fanchunshuai
 * @CreateTime：2020-12-02 23:07:13
 *//*

@Service
@Slf4j
public class DataSourceServiceImpl implements DataSourceService {


    @Resource
    private DataSourceMapper dataSourceMapper;



    @Resource
    private DataSourceDetailMapper dataSourceDetailMapper;

    @Autowired
    private ConfigServiceWrapper configServiceWrapper;

    @Autowired
    private RestTemplate restTemplate;


    */
/**
     * 初始化缓存，key:datasource
     * value:对应的数据内容，json字符串
     *//*

    private static final Cache<String, String> dataSourceCache = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.SECONDS)
            .maximumSize(10_000)
            .build();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(DataSourceVO dataSourceVo) throws Exception {

        if (dataSourceVo.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
            dataSourceVo.setSourceCode(dataSourceVo.getDataId() + "." + dataSourceVo.getGroupId());
        }
        //check
        DataSourceDO oldDO = dataSourceMapper.getBySourceCode(dataSourceVo.getSourceCode());
        if (oldDO != null) {
            //todo throw exception
            //return ResultDto.fail("500","sourceCode重复!");

        }

        ResultDto resultDto = new ResultDto();
        DataSourceDO dataSourceDO = cglibConvertService.copyPropertity(DataSourceDO.class, dataSourceVo);
        //本地缓存
        if (dataSourceDO.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            //存储--枚举类型
            if (dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
                    || dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode()) {
                dataSourceMapper.insert(dataSourceDO);
                DataSourceDetailDO dataSourceDetailDO = new DataSourceDetailDO();
                dataSourceDetailDO.setDataContentJson(dataSourceVo.getDataContentJson());
                dataSourceDetailDO.setDataSourceId(dataSourceDO.getId());
                dataSourceDetailMapper.insert(dataSourceDetailDO);
                return resultDto;
            }
        }

        dataSourceMapper.insert(dataSourceDO);
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
        DataSourceDO dataSourceDO = dataSourceMapper.getById(id);
        if (dataSourceDO == null) {
            return resultDataDto.setErrorCodeMsg("查询数据为空！");
        }
        DataSourceVO dataSourceVo = cglibConvertService.copyPropertity(DataSourceVO.class, dataSourceDO);
        if (dataSourceDO.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            //存储--枚举类型
            if (dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_ENUM.getCode()
                    || dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_CUSTOM.getCode()) {
                DataSourceDetailDO dataSourceDetailDO = dataSourceDetailMapper.getByDataSourceId(dataSourceDO.getId());
                dataSourceVo.setDataContentJson(dataSourceDetailDO.getDataContentJson());
            }
        }

        if (dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
            String sourceCode = dataSourceDO.getSourceCode();
            String groupId = sourceCode.substring(sourceCode.lastIndexOf(".") + 1);
            String dataId = sourceCode.substring(0, sourceCode.lastIndexOf("."));
            String content = configServiceWrapper.getConfig(dataId, groupId);
            dataSourceVo.setDataContentJson(content);
        }

        resultDataDto.setData(dataSourceVo);
        return resultDataDto;
    }

    @Override
    public ResultDataDto<List<DataSourceVO>> getAll() throws Exception {
        ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto<List<DataSourceVO>>();
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.getAll();

        return resultDataDto;
    }

    @Override
    public ResultDto update(DataSourceVO dataSourceVo) throws Exception {
        ResultDto resultDto = new ResultDto();

        return resultDto;
    }

    @Override
    public ResultDataDto<String> getDataSourceDetail(String dataSourceCode) throws Exception {
        ResultDataDto resultDataDto = new ResultDataDto();

        String dataContent = dataSourceCache.getIfPresent(dataSourceCode);
        if(StringUtils.isNotBlank(dataContent)){
            resultDataDto.setData(dataContent);
            return resultDataDto;
        }
        DataSourceDO dataSourceDO = dataSourceMapper.getBySourceCode(dataSourceCode);
        if (dataSourceDO == null) {
            return ResultDataDto.fail("500","查询数据为空!");
        }

        //本地持久化
        if (dataSourceDO.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            DataSourceDetailDO dataSourceDetailDO = dataSourceDetailMapper.getByDataSourceId(dataSourceDO.getId());
            if (dataSourceDetailDO == null || StringUtils.isEmpty(dataSourceDetailDO.getDataContentJson())) {
                return ResultDataDto.fail("500","查询数据为空!");
            }
            dataSourceCache.put(dataSourceCode,dataSourceDetailDO.getDataContentJson());
            return resultDataDto.setData(dataSourceDetailDO.getDataContentJson());
        }
        //todo 远程动态获取---springboot http协议优先支持 dubbo泛化调用支持tcp协议
        //这里配置的数据源默认时全量，如果需要参数则需要DataFactoryRequestFieldRuleBean2定义远程访问接口的参数和value进行动态获取，
        // 暂时先不支持
        //这里先支持全量数据的动态获取，默认进行缓存

        //todo 2.service api对接
        else {
            //nacos数据源
            if (dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
                String groupId = dataSourceCode.substring(dataSourceCode.lastIndexOf(".") + 1);
                String dataId = dataSourceCode.substring(0, dataSourceCode.lastIndexOf("."));
                List<KVPair<String, String>> list = configServiceWrapper.getConfigList(dataId, groupId);
                dataSourceCache.put(dataSourceCode,JSON.toJSONString(list));
                return resultDataDto.setData(JSON.toJSONString(list));
            } else if (dataSourceDO.getSourceType().intValue() == DataSourceTypeEnum.FROM_SERVICE_API.getCode()) {
                ResultDataDto remoteResultDataDto = restTemplate.getForObject(dataSourceDO.getUrl(), ResultDataDto.class);
                dataSourceCache.put(dataSourceCode,JSON.toJSONString(remoteResultDataDto.getData()));
                return resultDataDto.setData(JSON.toJSONString(remoteResultDataDto.getData()));
            }
        }

        return ResultDataDto.fail("500","查询数据为空!");
    }

    @Override
    public ResultDto updateStatus(Long id, Integer status) {
        int rows = dataSourceMapper.updateStatus(id, status);
        if(rows == 1){
            return new ResultDto();
        }else {
            return ResultDto.fail("500","更新失败");
        }
    }

    @Override
    public PageBO getPage(PageBO pageBO) {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.getPage(pageBO);
        ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto();
        return null;
    }

}*/
