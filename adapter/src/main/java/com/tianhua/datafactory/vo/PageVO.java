package com.tianhua.datafactory.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tianhua.datafactory.domain.bo.bean.PageBean;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Description:
 * date: 2022/4/24
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class PageVO<T> {
    public String orderBy;
    public String orderDir;
    /**
     * 当前页数
     */
    public Integer page;

    /**
     * 每页要展示的数量
     */
    public Integer perPage;

    /**
     * 分页接口返回的总数量
     */
    public Integer count;

    /**
     * 分页接口返回的当前页数据
     */
    public List<T> rows;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @JSONField(serialize = false)
    public PageBean getPageBean(){
        PageBean pageBean = new PageBean();
        pageBean.setPage(page);
        pageBean.setPerPage(perPage);
        pageBean.setCount(count);
        if(StringUtils.isNotEmpty(orderBy) && StringUtils.isNotEmpty(orderDir)){
            pageBean.setOrderBy(orderBy);
            pageBean.setOrderDir(orderDir);
        }
        pageBean.calculateStartAndEndRow();
        pageBean.dealOrderByInfo();
        return pageBean;
    }


}
