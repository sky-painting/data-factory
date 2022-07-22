package com.tianhua.datafactory.vo.query;

import com.tianhua.datafactory.domain.bo.PageBean;
import com.tianhua.datafactory.vo.PageVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * date: 2022/6/2
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class ModelMappingQueryVO extends PageVO {

    /**  **/
    String projectCode;

    /** 映射类型 **/
    String mappingType;

    /**
     * 映射模型类名 or表名
     */
    String mappingClass;


    public PageBean getPageBean(){
        PageBean pageBean = super.getPageBean();
        Map<String,Object> query = new HashMap<>();
        if(StringUtils.isNotEmpty(projectCode)){
            query.put("projectCode",projectCode);
        }
        if(StringUtils.isNotEmpty(mappingClass)){
            query.put("mappingClass",mappingClass);
        }
        if(StringUtils.isNotEmpty(mappingType)){
            query.put("mappingType",mappingType);
        }

        pageBean.setQuery(query);
        return pageBean;
    }

}
