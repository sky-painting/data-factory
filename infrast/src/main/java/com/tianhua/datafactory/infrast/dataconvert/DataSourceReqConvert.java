package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceReqConfigBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceReqConfigDO;
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
public interface DataSourceReqConvert {
    DataSourceReqConvert INSTANCE = Mappers.getMapper(DataSourceReqConvert.class);

    /**
     *
     * @Description:
     * @return ColumnBO
     */
    DataSourceReqConfigBO do2bo(DataSourceReqConfigDO dataSourceReqConfigDO);
    /**
     *
     * @Description:
     * @return List<ColumnBO>
     */
    List<DataSourceReqConfigBO> doList2boList(List<DataSourceReqConfigDO> dataSourceReqConfigDOList);
    /**
     *
     * @Description:
     * @return ColumnModelDO
     */
    DataSourceReqConfigDO bo2do(DataSourceReqConfigBO dataSourceReqConfigBO);
    /**
     *
     * @Description:
     * @return List<ColumnBO>
     */
    List<DataSourceReqConfigDO> boList2doList(List<DataSourceReqConfigBO> dataSourceReqConfigDOList);

}
