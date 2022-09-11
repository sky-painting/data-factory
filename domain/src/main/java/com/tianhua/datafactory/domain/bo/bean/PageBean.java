package com.tianhua.datafactory.domain.bo.bean;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2022/4/24
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class PageBean<T>{
    private String orderBy;
    private String orderDir;
    /**
     * 当前页数
     */
    private Integer page = 1;

    /**
     * 每页要展示的数量
     */
    private Integer perPage = 5;

    /**
     * 分页接口返回的总数量
     */
    private Integer count;

    /**
     * 分页接口返回的当前页数据
     */
    private List<T> rows;

    /**
     * 起始行
     */
    private Integer startRow;
    /**
     * 末行
     */
    private Integer endRow;

    private String orderByInfo;

    private Map<String,Object> query;

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public String getOrderByInfo() {
        return orderByInfo;
    }

    public void setOrderByInfo(String orderByInfo) {
        this.orderByInfo = orderByInfo;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    /**
     * 计算起止行号
     */
    public void calculateStartAndEndRow() {
        this.startRow = this.page > 0 ? (this.page - 1) * this.perPage : 0;
        this.endRow =  this.perPage;
    }

    public void dealOrderByInfo(){
        if(this.orderBy != null && this.orderDir != null){
            this.orderByInfo = " order by "+ orderBy + " "+ this.orderDir;
        }
    }


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



}
