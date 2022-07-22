package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDO;
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
public interface DataSourceConvert {
    DataSourceConvert INSTANCE = Mappers.getMapper(DataSourceConvert.class);


    /**
     *
     * @Description:
     * @return ColumnBO
     */
    DataSourceBO do2bo(DataSourceDO dataSourceDO);
    /**
     *
     * @Description:
     * @return List<ColumnBO>
     */
    List<DataSourceBO> doList2boList(List<DataSourceDO> dataSourceDOS);
    /**
     *
     * @Description:
     * @return ColumnModelDO
     */
    DataSourceDO bo2do(DataSourceBO dataSourceBO);


}
