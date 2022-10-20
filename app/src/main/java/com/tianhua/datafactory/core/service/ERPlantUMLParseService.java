package com.tianhua.datafactory.core.service;


import com.tianhua.datafactory.domain.bo.model.TableBO;

import java.util.List;

/**
 * Description:
 * date: 2021/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface ERPlantUMLParseService {

    /**
     * 根据plantuml 内容构建数据库表模型
     * @param contentList
     * @return
     */
    List<TableBO> getPlantUmlContextBean(List<String> contentList);


}
