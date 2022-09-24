package com.tianhua.datafactory.vo.query;


import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:查询项目基本信息请求VO类
 * @Author:shenshuai
 * @CreateTime:2022-05-27 16:48:08
 * @version v1.0
 */
@Data
@ToString
public class ProjectQueryVO extends PageVO {

    private String  projectCode;

    private String  domainCode;

    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(projectCode)){
            query.put("projectCode",projectCode);
        }
        if(StringUtils.isNotEmpty(domainCode)){
            query.put("domainCode",domainCode);
        }
        pageBean.setQuery(query);
        return pageBean;
    }

}