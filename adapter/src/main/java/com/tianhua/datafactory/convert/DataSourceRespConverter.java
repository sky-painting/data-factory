package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.vo.datasource.DataSourceRespConfigVO;
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
public interface DataSourceRespConverter {
    DataSourceRespConverter INSTANCE = Mappers.getMapper(DataSourceRespConverter.class);

    /**
     *
     * @Description:
     * @return dataSourceBO
     */
    DataSourceRespConfigVO bo2VO(DataSourceRespConfigBO dataSourceBO);
    /**
     *
     * @Description:
     * @return DataSourceBO
     */
    DataSourceRespConfigBO vo2bo(DataSourceRespConfigVO dataSourceRespConfigVO);

    /**
     *
     * @Description:
     * @return dataSourceBOList
     */
    List<DataSourceRespConfigVO> bo2VOs(List<DataSourceRespConfigBO> dataSourceRespConfigBOList);


    /**
     *
     * @Description:
     * @return dataSourceBOList
     */
    List<DataSourceRespConfigBO> vo2BOs(List<DataSourceRespConfigVO> dataSourceRespConfigVOList);

}
