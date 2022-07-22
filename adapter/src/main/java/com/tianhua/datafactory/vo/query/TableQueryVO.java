package com.tianhua.datafactory.vo.query;


import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:查询数据库table模型信息请求VO类
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class TableQueryVO extends PageVO {


    /**  **/
     String projectCode;

    /**  **/
     String tableName;

     String tableComment;


    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(projectCode)){
            query.put("projectCode",projectCode);
        }
        if(StringUtils.isNotEmpty(tableName)){
            query.put("tableName",tableName);
        }
        if(StringUtils.isNotEmpty(tableComment)){
            query.put("tableComment",tableComment);
        }
        pageBean.setQuery(query);
        return pageBean;
    }
}