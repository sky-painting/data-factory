package com.tianhua.datafactory.domain.repository;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;

import java.util.List;

/**
 * Description:
 * date: 2022/5/29
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface DataSourceRepository {


    /**
     *
     * @Title: save
     * @Description:新增
     * @author:
     * @param @param dto
     * @param @param userId
     * @return ResultDto    返回类型
     * @throws
     */
    public Boolean regist(DataSourceBO dataSourceBO);

    /**
     *
     * @Title: delete
     * @Description: 通过id删除数据
     * @author:
     * @param @param id
     * @return ResultDto    返回类型
     * @throws
     */
    public Boolean delete(Long id);

    /**
     *
     * @Title: getById
     * @Description: 通过id查询
     * @author:
     * @param @param id
     * @return ResultDataDto<DataSourceVO>
     * @throws
     */
    public DataSourceBO getById(Long id) throws Exception;

    /**
     *
     * @Title: getAll
     * @Description:查询所有数据
     * @author:
     * @return ResultDataDto<List<DataSourceVO>>
     * @throws
     */
    public List<DataSourceBO> getAll() throws Exception;

    /**
     *
     * @Title: update
     * @Description:修改
     * @author:
     * @param  dataSourceBO
     * @return Boolean
     * @throws
     */
    public Boolean update(DataSourceBO dataSourceBO) throws Exception;

    /**
     * 根据数据源code获取数据源详情-包括数据源信息
     * @param dataSourceCode
     * @return
     * @throws Exception
     */
    public String getDataSourceDetail(String dataSourceCode) throws Exception;


    /**
     * 修改数据源状态
     * @param id
     * @param status
     * @return
     */
    public Boolean updateStatus(Long id, Integer status);


    /**
     * 分页获取数据源
     * @param pageBean
     * @return
     */
    public PageBean getPageList(PageBean pageBean);


}
