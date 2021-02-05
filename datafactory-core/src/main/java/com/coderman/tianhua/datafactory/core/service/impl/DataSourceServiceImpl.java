package com.coderman.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldRuleBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceFieldRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataSourceQueryDTO;
import com.coderman.tianhua.datafactory.core.entity.DataSourceDetailEntity;
import com.coderman.tianhua.datafactory.core.entity.DataSourceEntity;
import com.coderman.tianhua.datafactory.core.enums.DataSourceTypeEnum;
import com.coderman.tianhua.datafactory.core.enums.VisitStrategyEnums;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceDetailMapper;
import com.coderman.tianhua.datafactory.core.mapper.DataSourceMapper;
import com.coderman.tianhua.datafactory.core.service.ConfigServiceWrapper;
import com.coderman.tianhua.datafactory.core.service.DataSourceService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.kvpair.KVPair;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 初始化缓存，key:datasource
     * value:对应的数据内容，json字符串
     */
    private static final Cache<String, String> dataSourceCache = Caffeine.newBuilder()
            .expireAfterWrite(15, TimeUnit.SECONDS)
            .maximumSize(10_000)
            .build();

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

        dataSourceMapper.insert(dataSourceEntity);
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
            String content = configServiceWrapper.getConfig(dataId, groupId);
            dataSourceVo.setDataContentJson(content);
        }

        resultDataDto.setData(dataSourceVo);
        return resultDataDto;
    }

    @Override
    public ResultDataDto<List<DataSourceVO>> getAll() throws Exception {
        ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto<List<DataSourceVO>>();
        List<DataSourceEntity> dataSourceEntityList = dataSourceMapper.getAll();
        List<DataSourceVO> dataSourceVOList = cglibConvertService.copyPropertities(DataSourceVO.class, dataSourceEntityList);
        resultDataDto.setData(dataSourceVOList);
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
    public ResultDataDto<String> getDataSourceDetail(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        ResultDataDto resultDataDto = new ResultDataDto();
        String dataSourceCode = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean().getDataSourceCode();
        String dataContent = dataSourceCache.getIfPresent(dataSourceCode);
        if(StringUtils.isNotBlank(dataContent)){
            resultDataDto.setData(dataContent);
            return resultDataDto;
        }
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
            dataSourceCache.put(dataSourceCode,dataSourceDetailEntity.getDataContentJson());
            return resultDataDto.setData(dataSourceDetailEntity.getDataContentJson());
        }
        //todo 远程动态获取---springboot http协议优先支持 dubbo泛化调用支持tcp协议
        //这里配置的数据源默认时全量，如果需要参数则需要DataFactoryRequestFieldRuleBean2定义远程访问接口的参数和value进行动态获取，
        // 暂时先不支持
        //这里先支持全量数据的动态获取，默认进行缓存

        //todo 2.service api对接
        else {
            //nacos数据源
            if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_NACOS.getCode()) {
                String groupId = dataSourceCode.substring(dataSourceCode.lastIndexOf(".") + 1);
                String dataId = dataSourceCode.substring(0, dataSourceCode.lastIndexOf("."));
                List<KVPair<String, String>> list = configServiceWrapper.getConfigList(dataId, groupId);
                dataSourceCache.put(dataSourceCode,JSON.toJSONString(list));
                return resultDataDto.setData(JSON.toJSONString(list));
            } else if (dataSourceEntity.getSourceType().intValue() == DataSourceTypeEnum.FROM_SERVICE_API.getCode()) {

                DataBuildRequestFieldRuleBean dataBuildRequestFieldRuleBean = dataSourceFieldRequestBean.getDataFactoryRequestFieldBean().getDataFactoryRequestFieldRuleBean();
                ResultDataDto remoteResultDataDto;
                if(dataBuildRequestFieldRuleBean != null && dataBuildRequestFieldRuleBean.getParameterMap() != null && !dataBuildRequestFieldRuleBean.getParameterMap().isEmpty()){
                   log.info("url = "+dataSourceEntity.getUrl()+",pam = "+JSON.toJSONString(dataBuildRequestFieldRuleBean.getParameterMap()));

                    Map<String,Object> param = dataBuildRequestFieldRuleBean.getParameterMap();
                    StringBuilder builder = new StringBuilder("?");
                    param.forEach((k,v)->{
                        builder.append(k+"="+v.toString()+"&");
                    });
                    String paramStr = builder.toString().substring(0,builder.length() - 1);
                    log.info("paramStr = "+paramStr+",dataSourceEntity.getUrl()+paramStr= "+dataSourceEntity.getUrl()+paramStr);
                    remoteResultDataDto = restTemplate.getForObject(dataSourceEntity.getUrl()+paramStr, ResultDataDto.class);
                }else {
                    remoteResultDataDto = restTemplate.getForObject(dataSourceEntity.getUrl(), ResultDataDto.class);
                }
                dataSourceCache.put(dataSourceCode,JSON.toJSONString(remoteResultDataDto.getData()));
                return resultDataDto.setData(JSON.toJSONString(remoteResultDataDto.getData()));
            }
        }

        return ResultDataDto.setNullErrorMsg("查询数据为空!");
    }

    @Override
    public ResultDto updateStatus(Long id, Integer status) {
        int rows = dataSourceMapper.updateStatus(id, status);
        if(rows == 1){
            return new ResultDto();
        }else {
            return ResultDto.setErrorCodeMsg("更新失败");
        }
    }

    @Override
    public ResultDataDto<List<DataSourceVO>> getPage(DataSourceQueryDTO dataSourceQueryDTO) {
        List<DataSourceEntity> dataSourceEntityList = dataSourceMapper.getPage(dataSourceQueryDTO);
        ResultDataDto<List<DataSourceVO>> resultDataDto = new ResultDataDto();
        try {
            List<DataSourceVO> dataSourceVOList = cglibConvertService.copyPropertities(DataSourceVO.class,dataSourceEntityList);
            return resultDataDto.setData(dataSourceVOList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultDataDto.setInvokeErrorMsg("查询失败");
    }

}