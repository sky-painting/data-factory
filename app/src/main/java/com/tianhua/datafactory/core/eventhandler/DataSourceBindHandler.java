package com.tianhua.datafactory.core.eventhandler;

import com.tianhua.datafactory.domain.ability.TransEventHandler;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.event.DataSourceBindEvent;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Description:在构建数据模型之间的映射过程中，发出模型映射事件，
 * 辅助绑定数据源
 * date: 2022/6/10
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Component
public class DataSourceBindHandler implements TransEventHandler<DataSourceBindEvent> {
    @Autowired
    private ModelQueryRepository modelQueryRepository;


    @Autowired
    private ModelRepository modelRepository;


    @Override
    @EventListener
    public void handleTransEvent(DataSourceBindEvent baseEvent) {
        ModelMappingBO modelMappingBO = baseEvent.getModelMappingBO();
        List<FieldBO> firstFieldBOList = modelQueryRepository.getModelField(modelMappingBO.getProjectCode(),modelMappingBO.getMappingClassFirst());
        List<FieldBO> secondFieldBOList = modelQueryRepository.getModelField(modelMappingBO.getProjectCode(),modelMappingBO.getMappingClassSecond());

        FieldBO firstFieldBO = firstFieldBOList.stream().filter(fieldBO -> fieldBO.getFieldName().equals(modelMappingBO.getMappingFieldFirst())).findFirst().get();
        FieldBO secondFieldBO = secondFieldBOList.stream().filter(fieldBO -> fieldBO.getFieldName().equals(modelMappingBO.getMappingFieldSecond())).findFirst().get();

        if(!StringUtils.isEmpty(firstFieldBO.getFieldExtBO().getDataSourceCode()) &&
                !StringUtils.isEmpty(secondFieldBO.getFieldExtBO().getDataSourceCode())) {
            return;
        }


        if(!StringUtils.isEmpty(firstFieldBO.getFieldExtBO().getDataSourceCode()) &&
                StringUtils.isEmpty(secondFieldBO.getFieldExtBO().getDataSourceCode())){
            secondFieldBO.setFieldExtBO(firstFieldBO.getFieldExtBO());
            ParamModelBO paramModelBO = ParamModelBO.getInstance(null);
            paramModelBO.addField(secondFieldBO);
            modelRepository.updateParamModel(paramModelBO);
        }

        if(StringUtils.isEmpty(firstFieldBO.getFieldExtBO().getDataSourceCode()) &&
                !StringUtils.isEmpty(secondFieldBO.getFieldExtBO().getDataSourceCode())){
            firstFieldBO.setFieldExtBO(secondFieldBO.getFieldExtBO());

            ParamModelBO paramModelBO = ParamModelBO.getInstance(null);
            paramModelBO.addField(secondFieldBO);
            modelRepository.updateParamModel(paramModelBO);
        }


    }
}
