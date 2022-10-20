package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;

import java.util.List;

/**
 * Description
 * 类型转换适配层
 * 对接口入参，出参模型做适配
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface DataTypeAdapter {
    /**
     * 根据类型构建属性数据请求模型列表
     * @param genericTypeBO
     * @return
     */
    List<DataBuildRequestFieldBO> buildFieldList(GenericTypeBO genericTypeBO) throws Exception;

}
