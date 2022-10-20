package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.vo.datasource.DataSourceReqConfigVO;
import org.mapstruct.Mapper;
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
@Mapper
public interface DataSourceReqConverter {
    DataSourceReqConverter INSTANCE = Mappers.getMapper(DataSourceReqConverter.class);

    /**
     *
     * @Description:
     * @return dataSourceBO
     */
    DataSourceReqConfigVO bo2VO(DataSourceReqConfigBO dataSourceReqConfigBO);
    /**
     *
     * @Description:
     * @return dataSourceRespConfigVO
     */
    DataSourceReqConfigBO vo2bo(DataSourceReqConfigVO dataSourceRespConfigVO);

    /**
     *
     * @Description:
     * @return dataSourceBOList
     */
    List<DataSourceReqConfigVO> bo2VOs(List<DataSourceReqConfigBO> dataSourceRespConfigBOList);

    /**
     *
     * @Description:
     * @return dataSourceBOList
     */
    List<DataSourceReqConfigBO> vo2BOs(List<DataSourceReqConfigVO> dataSourceReqConfigVOList);

}
