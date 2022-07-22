package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.util.List;

/**
 * Description:
 * date: 2022/5/29
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface DataSourceQueryRepository {

    /**
     * 分页查询
     * @param pageBean
     * @return
     */
    public PageBean getDataSourcePage(PageBean pageBean);

    /**
     * 搜索并重新组织子数据源
     * @param content
     * @return
     */
    public List<DataSourceBO> search(String content);

    /**
     * 根据数据源编码获取数据源信息
     * @param dataSourceCode
     * @return
     */
    public DataSourceBO getByDataSourceCode(String dataSourceCode);

}
