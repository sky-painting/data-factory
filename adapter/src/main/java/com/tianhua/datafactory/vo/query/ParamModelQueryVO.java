package com.tianhua.datafactory.vo.query;


import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:查询参数模型信息请求VO类
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ParamModelQueryVO extends PageVO {


    /**  **/
     String projectCode;

    /**  **/
     String paramClassName;

    /**
     * 模型后缀
     */
    String modelSuffix;

    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(projectCode)){
            query.put("projectCode",projectCode);
        }
        if(StringUtils.isNotEmpty(paramClassName)){
            query.put("paramClassName",paramClassName);
        }

        if(StringUtils.isNotEmpty(modelSuffix)){
            query.put("modelSuffix",modelSuffix);
        }
        pageBean.setQuery(query);
        return pageBean;
    }
}