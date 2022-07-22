package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.vo.datasource.DataSourceReqConfigVO;
import com.tianhua.datafactory.vo.datasource.DataSourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Description:
 * date: 2022/5/30
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Mapper(imports = {
        DataSourceRespConverter.class,
        DataSourceReqConverter.class,
        KVPairConverter.class
})
public interface DataSourceConverter {
    DataSourceConverter INSTANCE = Mappers.getMapper(DataSourceConverter.class);

    /**
     * bo聚合对象转换为vo聚合对象
     * @Description:
     * @return dataSourceBO
     */
    @Mappings({
            @Mapping(target = "dataSourceReqConfigList",expression = "java(DataSourceReqConverter.INSTANCE.bo2VOs(dataSourceBO.getDataSourceReqConfigList()))"),
            @Mapping(target = "dataSourceRespConfigList",expression = "java(DataSourceRespConverter.INSTANCE.bo2VOs(dataSourceBO.getDataSourceRespConfigList()))"),
            @Mapping(target = "kvPairList",expression = "java(KVPairConverter.INSTANCE.BOs2VOs(dataSourceBO.getKvPairList()))")
    })
    DataSourceVO bo2VO(DataSourceBO dataSourceBO);

    /**
     * vo聚合对象转换为bo聚合对象
     * @param dataSourceVO
     * @return
     */
    @Mappings({
        @Mapping(target = "dataSourceReqConfigList",expression = "java(DataSourceReqConverter.INSTANCE.vo2BOs(dataSourceVO.getDataSourceReqConfigList()))"),
        @Mapping(target = "dataSourceRespConfigList",expression = "java(DataSourceRespConverter.INSTANCE.vo2BOs(dataSourceVO.getDataSourceRespConfigList()))"),
            @Mapping(target = "kvPairList",expression = "java(KVPairConverter.INSTANCE.VOs2BOs(dataSourceVO.getKvPairList()))")

    })
    DataSourceBO vo2bo(DataSourceVO dataSourceVO);

    /**
     *
     * @Description:
     * @return dataSourceBOList
     */
    List<DataSourceVO> bo2VOs(List<DataSourceBO> dataSourceBOList);

}
