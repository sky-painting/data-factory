package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.ModelMappingBO;
import com.tianhua.datafactory.domain.enums.ModelValueMappingType;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * date: 2022/6/6
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ModelMappingBuilderService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelQueryRepository modelQueryRepository;

    /**
     * 批量构建映射模型
     *
     * @param modelMappingBO
     */
    @Transactional(rollbackFor = Exception.class)
    public void buildModelMappingBatch(ModelMappingBO modelMappingBO) {
        List<FieldBO> firstFieldBOList = modelQueryRepository.getModelField(modelMappingBO.getProjectCode(), modelMappingBO.getMappingClassFirst());
        List<FieldBO> secondFieldBOList = modelQueryRepository.getModelField(modelMappingBO.getProjectCode(), modelMappingBO.getMappingClassSecond());
        List<ModelMappingBO> modelMappingBOList = buildModelMappingList(modelMappingBO.getProjectCode(), firstFieldBOList,secondFieldBOList);


        List<ModelMappingBO> modelMappingBOS = modelQueryRepository.getModelMappingListByProjectCode(modelMappingBO.getProjectCode());
        Set<String> mappingSet = new HashSet<>();
        modelMappingBOS.stream().forEach(modelMappingBO1 -> {
            mappingSet.addAll(modelMappingBO1.buildMappingKey());
        });

        for (ModelMappingBO modelMappingBO1 : modelMappingBOList){
            boolean contains = false;
            for (String key : modelMappingBO1.buildMappingKey()){
                if(mappingSet.contains(key)){
                    contains = true;
                }
            }
            if(!contains){
                modelRepository.saveModelMapping(modelMappingBO1);
            }
        }
    }


    private List<ModelMappingBO> buildModelMappingList(String projectCode, List<FieldBO> firstFieldBOList, List<FieldBO> secondFieldBOList) {
        List<ModelMappingBO> modelMappingBOList = new ArrayList<>();

        for (FieldBO firstFieldBO : firstFieldBOList) {
            for (FieldBO secondFieldBO : secondFieldBOList) {
                //如果命名不一致则忽略，除非是数据库模型这里需要特殊处理
                if (!firstFieldBO.getFieldName().equals(secondFieldBO.getFieldName())) {
                    continue;
                }
                ModelMappingBO modelMappingBO = new ModelMappingBO();
                modelMappingBO.setMappingClassFirst(firstFieldBO.getParamClassName());
                modelMappingBO.setMappingClassSecond(secondFieldBO.getParamClassName());
                modelMappingBO.setMappingFieldFirst(firstFieldBO.getFieldName());
                modelMappingBO.setMappingFieldSecond(secondFieldBO.getFieldName());
                modelMappingBO.setProjectCode(projectCode);
                if (firstFieldBO.getFieldType().equals(secondFieldBO.getFieldType())) {
                    modelMappingBO.setMappingType(ModelValueMappingType.DIRECT_MAPPING.getType());
                } else {
                    modelMappingBO.setMappingType(ModelValueMappingType.VALUE_MAPPING.getType());
                }
                modelMappingBOList.add(modelMappingBO);
            }
        }

        return modelMappingBOList;
    }
}
