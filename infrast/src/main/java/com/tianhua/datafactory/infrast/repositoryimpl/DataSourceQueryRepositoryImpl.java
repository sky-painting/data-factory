package com.tianhua.datafactory.infrast.repositoryimpl;

import com.tianhua.datafactory.domain.ability.KVPairService;
import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDO;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceDetailMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceReqConfigMapper;
import com.tianhua.datafactory.infrast.dao.mapper.DataSourceResConfigMapper;
import com.tianhua.datafactory.infrast.dataconvert.DataSourceConvert;
import com.tianhua.datafactory.infrast.dataconvert.DataSourceRespConvert;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * date: 2022/5/31
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class DataSourceQueryRepositoryImpl  implements DataSourceQueryRepository {

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

    @Override
    public PageBean getDataSourcePage(PageBean pageBean) {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.getPage(pageBean);
        pageBean.setRows(DataSourceConvert.INSTANCE.doList2boList(dataSourceDOList));
       // pageBean.setPage();
        return pageBean;
    }


    @Override
    public List<DataSourceBO> search(String content) {
        List<DataSourceDO> dataSourceDOList = dataSourceMapper.search(content);
        if (CollectionUtils.isEmpty(dataSourceDOList)) {
            return Lists.newArrayList();
        }

        List<DataSourceBO> resultList = new ArrayList<>();
        List<DataSourceBO> dataSourceBOList = DataSourceConvert.INSTANCE.doList2boList(dataSourceDOList);
        Set<String> dataSourceCodeSet = new HashSet<>();
        for (DataSourceBO dataSourceBO : dataSourceBOList) {
            List<DataSourceRespConfigBO> dataSourceRespConfigBOList = DataSourceRespConvert.INSTANCE.doList2boList(dataSourceResConfigMapper.getByDataSourceId(dataSourceBO.getId()));
            if (CollectionUtils.isNotEmpty(dataSourceRespConfigBOList)) {
                //构建子数据源编码，辅助数据定位
                for (DataSourceRespConfigBO dataSourceRespConfigBO : dataSourceRespConfigBOList) {
                    DataSourceBO dataSubSourceBO = new DataSourceBO();
                    dataSubSourceBO.setSourceName(dataSourceBO.getSourceName()+"#"+dataSourceRespConfigBO.getFieldDesc());
                    dataSubSourceBO.setSourceCode(dataSourceBO.getSourceCode()+"#"+dataSourceRespConfigBO.getFieldKey());
                    resultList.add(dataSubSourceBO);
                }
            }else {
                if(dataSourceBO.isLocalEnum()){
                    KVPairBO kvPairBO = KVPairBO.instance();
                    kvPairBO.setParentKey(dataSourceBO.getProviderService());
                    List<KVPairBO> kvPairBOList = kvPairService.getList(kvPairBO);
                    if(CollectionUtils.isNotEmpty(kvPairBOList)){
                        for (KVPairBO kvPairBO1 : kvPairBOList){
                            DataSourceBO dataSubSourceBO = new DataSourceBO();
                            String dataSourceCode = kvPairBO1.getGroupKey()+"#"+kvPairBO1.getKey();
                            if(dataSourceCodeSet.contains(dataSourceCode)){
                                continue;
                            }
                            dataSourceCodeSet.add(dataSourceCode);
                            dataSubSourceBO.setSourceName(dataSourceCode);
                            dataSubSourceBO.setSourceCode(dataSourceCode);
                            resultList.add(dataSubSourceBO);
                        }
                    }
                }else {
                    resultList.add(dataSourceBO);
                }
            }
        }

        return resultList;
    }

    @Override
    public DataSourceBO getByDataSourceCode(String dataSourceCode) {
        if(dataSourceCode == null){
            return null;
        }
        if(dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode.split("#")[0];
        }
        DataSourceBO dataSourceBO = DataSourceConvert.INSTANCE.do2bo(dataSourceMapper.getBySourceCode(dataSourceCode));
        return dataSourceBO;
    }

}
