package com.tianhua.datafactory.infrast.repositoryimpl;

import com.tianhua.datafactory.domain.bo.model.*;

import java.util.List;

import com.tianhua.datafactory.domain.enums.ApiModelFieldStatusEnum;
import com.tianhua.datafactory.domain.event.DataSourceBindEvent;
import com.tianhua.datafactory.domain.repository.ModelRepository;

import com.tianhua.datafactory.domain.util.AppEventPublisher;
import com.tianhua.datafactory.infrast.dao.dataobject.FieldModelDO;
import com.tianhua.datafactory.infrast.dao.dataobject.ParamModelDO;
import com.tianhua.datafactory.infrast.dao.dataobject.TableModelDO;
import com.tianhua.datafactory.infrast.dao.mapper.*;
import com.tianhua.datafactory.infrast.dataconvert.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;


/**
 * @Description:接口实现类
 * @Author：
 * @CreateTime：2022-05-27 17:45:38
 * @version v1.0
 */
@Service
public class ModelRepositoryImpl  implements ModelRepository{

	private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TableModelMapper tableModelMapper;


    @Autowired
    private ColumnModelMapper columnModelMapper;


    @Autowired
    private ParamModelMapper paramModelMapper;


    @Autowired
    private ModelSuffixConfigMapper modelSuffixConfigMapper;

    @Autowired
    private FieldModelMapper fieldModelMapper;

    @Autowired
    private ModelMappingConfigMapper modelMappingConfigMapper;


    @Autowired
    private AppEventPublisher appEventPublisher;



    @Override
	public boolean saveDBModel(TableBO tableBO){
        tableBO.init();
        tableBO.setStatus(ApiModelFieldStatusEnum.USING.getStatus());
        TableModelDO tableModelDO = TableConvert.INSTANCE.bo2do(tableBO);
        tableModelMapper.insert(tableModelDO);
        if(!CollectionUtils.isEmpty(tableBO.getColumnList())){
            for (ColumnBO columnBO : tableBO.getColumnList()){
                columnBO.setTableId(tableModelDO.getId());
                columnBO.init();
                columnBO.setStatus(ApiModelFieldStatusEnum.USING.getStatus());
                columnModelMapper.insert(ColumnConvert.INSTANCE.bo2do(columnBO));
            }
        }

        return true;
    }

    @Override
	public boolean updateDBModel(TableBO tableBO){
        tableModelMapper.update(TableConvert.INSTANCE.bo2do(tableBO));
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(tableBO.getColumnList())){
            columnModelMapper.deleteById(tableBO.getId());
            for (ColumnBO columnBO : tableBO.getColumnList()){
                columnBO.setTableId(tableBO.getId());
                columnModelMapper.insert(ColumnConvert.INSTANCE.bo2do(columnBO));
            }
        }
        return true;
    }

    @Override
	public List<TableBO> getDbErByProjectCode(String projectCode){
        List<TableModelDO> tableModelDOList = tableModelMapper.getByProjectCode(projectCode);
        List<TableBO> tableBOList = TableConvert.INSTANCE.doList2boList(tableModelDOList);
        for (TableBO tableBO : tableBOList){
            List<ColumnBO> columnBOS = ColumnConvert.INSTANCE.doList2boList(columnModelMapper.getByTableId(tableBO.getId()));
            tableBO.setColumnList(columnBOS);
        }
        return tableBOList;
    }

    @Override
	public boolean saveParamModel(ParamModelBO paramModelBO){
        paramModelBO.init();
        paramModelBO.using();
        paramModelMapper.insert(ParamModelConvert.INSTANCE.bo2do(paramModelBO));

        List<FieldBO> fieldBOList = paramModelBO.getFieldBeanList();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(fieldBOList)){
            for (FieldBO fieldBO : fieldBOList){
                fieldBO.setProjectCode(paramModelBO.getProjectCode());
                fieldBO.setParamClassName(paramModelBO.getParamClassName());


                fieldModelMapper.insert(FieldModelConvert.INSTANCE.bo2do(fieldBO));
            }
        }

        return true;
    }

    @Override
	public boolean updateParamModel(ParamModelBO paramModelBO){
        if(StringUtils.isNotEmpty(paramModelBO.getParamClassName())){
            paramModelBO.init();
            paramModelMapper.update(ParamModelConvert.INSTANCE.bo2do(paramModelBO));
        }

        List<FieldBO> fieldBOList = paramModelBO.getFieldBeanList();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(fieldBOList)){
            for (FieldBO fieldBO : fieldBOList){
                FieldModelDO fieldModelDO = fieldModelMapper.getByCodeField(paramModelBO.getProjectCode(), paramModelBO.getParamClassName(), fieldBO.getFieldName());
                if(fieldModelDO == null){
                    fieldModelMapper.insert(FieldModelConvert.INSTANCE.bo2do(fieldBO));
                }else {
                    fieldModelDO.setFieldType(fieldBO.getFieldType());
                    fieldModelDO.setFieldDesc(fieldBO.getFieldDesc());
                    fieldModelMapper.update(fieldModelDO);
                }
            }
        }

        return true;
    }

    @Override
	public List<ParamModelBO> getModelByProjectCode(String projectCode){
        List<ParamModelDO> paramModelDOList = paramModelMapper.getByProjectCode(projectCode);
        List<ParamModelBO> paramModelBOS = ParamModelConvert.INSTANCE.doList2boList(paramModelDOList);

        return paramModelBOS;
    }

    @Override
    public ParamModelBO getModel(String projectCode, String paramClassName) {
        ParamModelDO paramModelDO = paramModelMapper.getByParamClassName(projectCode, paramClassName);

        return ParamModelConvert.INSTANCE.do2bo(paramModelDO);
    }

    @Override
	public boolean saveModelSuffix(ModelSuffixConfigBO modelSuffixConfigBO){
        modelSuffixConfigMapper.insert(ModelSuffixConfigConvert.INSTANCE.bo2do(modelSuffixConfigBO));
        return true;
    }

    @Override
	public boolean updateModelSuffix(ModelSuffixConfigBO modelSuffixConfigBO){
        int effect = modelSuffixConfigMapper.update(ModelSuffixConfigConvert.INSTANCE.bo2do(modelSuffixConfigBO));
        return effect == 1;
    }

    @Override
    public boolean saveModelMapping(ModelMappingBO modelMappingBO) {

        modelMappingConfigMapper.insert(ModelMappingConvert.INSTANCE.bo2do(modelMappingBO));
        //发布模型映射事件
        appEventPublisher.publish(new DataSourceBindEvent(this,modelMappingBO));

        return true;
    }


}