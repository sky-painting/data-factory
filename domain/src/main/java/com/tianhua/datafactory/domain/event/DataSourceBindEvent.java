package com.tianhua.datafactory.domain.event;

import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;
import lombok.Data;

/**
 * Description:模型映射事件
 * date: 2022/6/10
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */

public class DataSourceBindEvent extends BaseEvent {

    private ModelMappingBO modelMappingBO;

    public DataSourceBindEvent(Object source, ModelMappingBO modelMappingBO) {
        super(source);
        this.modelMappingBO = modelMappingBO;
    }

    public ModelMappingBO getModelMappingBO() {
        return modelMappingBO;
    }
}
