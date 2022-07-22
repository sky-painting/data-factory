package com.tianhua.datafactory.vo.query;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * description: DataSourceQueryDTO <br>
 * date: 2021/1/27 23:45 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
public class DataSourceQueryVO  extends PageVO {
    /**
     * 数据源code
     */
    private String dataSourceCode;
    /**
     * 数据源名称
     */
    private String dataSourceName;

    /** 数据源类型/(regist,api,enum) **/
    private Integer sourceType;

    /** 状态(0正常,1过期) **/
    private Integer status;


    /**
     * 数据源的访问策略
     */
    private Integer visitStrategy;

    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(dataSourceCode)){
            query.put("dataSourceCode",dataSourceCode);
        }
        if(StringUtils.isNotEmpty(dataSourceName)){
            query.put("dataSourceName",dataSourceName);
        }

        if(sourceType != null){
            query.put("sourceType",sourceType);
        }

        if(status != null){
            query.put("status",status);
        }
        if(visitStrategy != null){
            query.put("visitStrategy",visitStrategy);
        }
        pageBean.setQuery(query);
        return pageBean;
    }



}
