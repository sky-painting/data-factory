package com.tianhua.datafactory.vo.query;


import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:查询api模型信息请求VO类
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ApiQueryVO  extends PageVO {

    /**  **/
     String apiType;

    /**  **/
     String apiSign;

    /**  **/
     String methodType;

    /**  **/
     String projectCode;

    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(apiType)){
            query.put("apiType",apiType);
        }
        if(StringUtils.isNotEmpty(methodType)){
            query.put("methodType",methodType);
        }
        if(StringUtils.isNotEmpty(apiSign)){
            query.put("apiSign",apiSign);
        }
        if(projectCode != null){
            query.put("projectCode",projectCode);
        }
        pageBean.setQuery(query);
        return pageBean;
    }
}