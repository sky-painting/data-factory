package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceRespConfigBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceRespConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Description:
 * date: 2022/5/29
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Mapper
public interface DataSourceRespConvert {
    DataSourceRespConvert INSTANCE = Mappers.getMapper(DataSourceRespConvert.class);

    /**
     *
     * @Description:
     * @return ColumnBO
     */
    DataSourceRespConfigBO do2bo(DataSourceRespConfigDO dataSourceRespConfigDO);
    /**
     *
     * @Description:
     * @return List<ColumnBO>
     */
    List<DataSourceRespConfigBO> doList2boList(List<DataSourceRespConfigDO> dataSourceRespConfigDOList);
    /**
     *
     * @Description:
     * @return ColumnModelDO
     */
    DataSourceRespConfigDO bo2do(DataSourceRespConfigBO dataSourceRespConfigBO);

    /**
     *
     * @Description:
     * @return List<ColumnBO>
     */
    List<DataSourceRespConfigDO> boList2doList(List<DataSourceRespConfigBO> dataSourceRespConfigBOList);
}
