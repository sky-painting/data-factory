package com.tianhua.datafactory.domain.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* Description:参考Mybatis_PageHelper的Page对象封装的DTO
* date: 2021/10/26
*
* @author shenshuai
* @version 1.0.0
* @since JDK 1.8
*/
public class PageBO<E> implements Serializable {

    private static final long serialVersionUID = -2470832822882514457L;
    /**
    * 页码，从1开始
    */
    private int currentPageNum;

    /**
    * 当前页的下一页
    */
    private int nextPageNum;

    /**
    * 当前页的上一页
    */
    private int prePageNum;
    /**
    * 页面大小
    */
    private int pageSize;
    /**
    * 起始行
    */
    private int startRow;
    /**
    * 末行
    */
    private int endRow;
    /**
    * 总数
    */
    private long totalRows;
    /**
    * 总页数
    */
    private int totalPages;
    /**
    * 包含count查询
    */
    private boolean count = true;
    /**
    * 分页合理化
    */
    private Boolean reasonable;
    /**
    * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
    */
    private Boolean pageSizeZero;
    /**
    * 进行count查询的列名
    */
    private String countColumn;
    /**
    * 排序
    */
    private String orderBy;
    /**
    * 只增加排序
    */
    private boolean orderByOnly;

    private List<E> Items;


    /**
      * 针对简单场景 参数的查询map处理
      */
    private Map<String, Object> queryMap;

        /**
        * 针对复杂场景 参数的查询queryDTO对象处理
        */
        private Object queryDTO;


        public Map<String, Object> getQueryMap() {
        return queryMap;
        }

        public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
        }

        public Object getQueryDTO() {
        return queryDTO;
        }

        public void setQueryDTO(Object queryDTO) {
        this.queryDTO = queryDTO;
        }

    public List<E> getItems() {
        return Items;
    }

    public PageBO<E> setItems(List<E> items) {
        Items = items;
        return this;
    }
}