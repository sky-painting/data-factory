package com.tianhua.datafactory.infrast.repositoryimpl;

import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tianhua.datafactory.domain.ability.KVPairService;
import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.enums.VisitStrategyEnums;
import com.tianhua.datafactory.domain.repository.DataSourceRepository;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.infrast.dao.dataobject.*;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceDetailMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceReqConfigMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceResConfigMapper;
import com.tianhua.datafactory.infrast.dataconvert.DataSourceConvert;
import com.tianhua.datafactory.infrast.dataconvert.DataSourceReqConvert;
import com.tianhua.datafactory.infrast.dataconvert.DataSourceRespConvert;
import com.tianhua.datafactory.infrast.dataconvert.ProjectConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * date: 2022/5/29
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class DataSourceRepositoryImpl implements DataSourceRepository {

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Resource
    private DataSourceDetailMapper dataSourceDetailMapper;


    @Resource
    private DataSourceReqConfigMapper dataSourceReqConfigMapper;

    @Resource
    private DataSourceResConfigMapper dataSourceResConfigMapper;

    @Autowired
    private KVPairService kvPairService;

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
    public Boolean regist(DataSourceBO dataSourceBO) {
        dataSourceBO.init();
        DataSourceDO dataSourceDO = DataSourceConvert.INSTANCE.bo2do(dataSourceBO);
        dataSourceMapper.insert(dataSourceDO);
        if (CollectionUtils.isNotEmpty(dataSourceBO.getDataSourceReqConfigList())) {
            List<DataSourceReqConfigDO> dataSourceReqConfigDOList = DataSourceReqConvert.INSTANCE.boList2doList(dataSourceBO.getDataSourceReqConfigList());
            for (DataSourceReqConfigDO dataSourceReqConfigDO : dataSourceReqConfigDOList) {
                dataSourceReqConfigDO.setDataSourceId(dataSourceDO.getId());
                dataSourceReqConfigMapper.insert(dataSourceReqConfigDO);
            }
        }

        if (CollectionUtils.isNotEmpty(dataSourceBO.getDataSourceRespConfigList())) {
            List<DataSourceRespConfigDO> dataSourceRespConfigDOList = DataSourceRespConvert.INSTANCE.boList2doList(dataSourceBO.getDataSourceRespConfigList());
            for (DataSourceRespConfigDO dataSourceRespConfigDO : dataSourceRespConfigDOList) {
                dataSourceRespConfigDO.setDataSourceId(dataSourceDO.getId());
                dataSourceResConfigMapper.insert(dataSourceRespConfigDO);
            }
        }

        if(CollectionUtils.isNotEmpty(dataSourceBO.getKvPairList())){
            kvPairService.batchInsert(dataSourceBO.getKvPairList());
        }
        return true;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("dataSourceService.delete,id=" + id);
        dataSourceMapper.deleteById(id);
        return true;
    }

    @Override
    public DataSourceBO getById(Long id) throws Exception {

        DataSourceBO dataSourceBO = DataSourceConvert.INSTANCE.do2bo(dataSourceMapper.getById(id));
        dataSourceBO.setDataSourceReqConfigList(DataSourceReqConvert.INSTANCE.doList2boList(dataSourceReqConfigMapper.getByDataSourceId(id)));
        dataSourceBO.setDataSourceRespConfigList(DataSourceRespConvert.INSTANCE.doList2boList(dataSourceResConfigMapper.getByDataSourceId(id)));

        KVPairBO kvPairBO = KVPairBO.instance();
        kvPairBO.setParentKey(dataSourceBO.getProviderService());
        dataSourceBO.setKvPairList(kvPairService.getList(kvPairBO));

        return dataSourceBO;
    }

    @Override
    public List<DataSourceBO> getAll() throws Exception {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.getAll();
        return DataSourceConvert.INSTANCE.doList2boList(dataSourceDOList);
    }

    @Override
    public Boolean update(DataSourceBO dataSourceBO) throws Exception {
        DataSourceDO dataSourceDO = DataSourceConvert.INSTANCE.bo2do(dataSourceBO);
        dataSourceMapper.update(dataSourceDO);
        if (CollectionUtils.isNotEmpty(dataSourceBO.getDataSourceReqConfigList())) {
            dataSourceReqConfigMapper.deleteByDataSourceId(dataSourceBO.getId());

            List<DataSourceReqConfigDO> dataSourceReqConfigDOList = DataSourceReqConvert.INSTANCE.boList2doList(dataSourceBO.getDataSourceReqConfigList());
            for (DataSourceReqConfigDO dataSourceReqConfigDO : dataSourceReqConfigDOList) {
                dataSourceReqConfigDO.setDataSourceId(dataSourceDO.getId());
                dataSourceReqConfigMapper.insert(dataSourceReqConfigDO);
            }
        }

        if (CollectionUtils.isNotEmpty(dataSourceBO.getDataSourceRespConfigList())) {

            dataSourceResConfigMapper.deleteByDataSourceId(dataSourceBO.getId());

            List<DataSourceRespConfigDO> dataSourceRespConfigDOList = DataSourceRespConvert.INSTANCE.boList2doList(dataSourceBO.getDataSourceRespConfigList());
            for (DataSourceRespConfigDO dataSourceRespConfigDO : dataSourceRespConfigDOList) {
                dataSourceRespConfigDO.setDataSourceId(dataSourceDO.getId());
                dataSourceResConfigMapper.insert(dataSourceRespConfigDO);
            }
        }
        return false;
    }

    @Override
    public String getDataSourceDetail(String dataSourceCode) throws Exception {
        ResultDataDto resultDataDto = new ResultDataDto();

        String dataContent = dataSourceCache.getIfPresent(dataSourceCode);
        if (StringUtils.isNotBlank(dataContent)) {
            return dataContent;
        }
        DataSourceDO dataSourceDO = dataSourceMapper.getBySourceCode(dataSourceCode);
        if (dataSourceDO == null) {
            // todo throw exception
            //return ResultDataDto.fail("500","查询数据为空!");
        }

        //本地持久化
        if (dataSourceDO.getVisitStrategy().intValue() == VisitStrategyEnums.LOCAL_CACHE.getCode()) {
            DataSourceDetailDO dataSourceDetailDO = dataSourceDetailMapper.getByDataSourceId(dataSourceDO.getId());
            if (dataSourceDetailDO == null || StringUtils.isEmpty(dataSourceDetailDO.getDataContentJson())) {
                // return ResultDataDto.fail("500","查询数据为空!");
            }
            dataSourceCache.put(dataSourceCode, dataSourceDetailDO.getDataContentJson());

        }
        //todo 远程动态获取---springboot http协议优先支持 dubbo泛化调用支持tcp协议
        //这里配置的数据源默认时全量，如果需要参数则需要DataFactoryRequestFieldRuleBean2定义远程访问接口的参数和value进行动态获取，
        // 暂时先不支持
        //这里先支持全量数据的动态获取，默认进行缓存

        //todo 2.service api对接
     /*   else {
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

        return ResultDataDto.fail("500","查询数据为空!");*/
        return "";
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        int rows = dataSourceMapper.updateStatus(id, status);
        return rows == 1;
    }

    @Override
    public PageBean getPageList(PageBean pageBean) {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.getPage(pageBean);
        pageBean.setRows(DataSourceConvert.INSTANCE.doList2boList(dataSourceDOList));
        pageBean.setCount(dataSourceMapper.getPageCount(pageBean));
        return pageBean;
    }


}
