package com.tianhua.datafactory.infrast.repositoryimpl;

import com.tianhua.datafactory.domain.bo.bean.PageBean;
import com.tianhua.datafactory.domain.bo.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tianhua.datafactory.domain.repository.ModelQueryRepository;

import com.tianhua.datafactory.infrast.dao.dataobject.*;
import com.tianhua.datafactory.infrast.dao.mapper.*;
import com.tianhua.datafactory.infrast.dataconvert.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description:接口实现类
 * @Author：
 * @CreateTime：2022-05-27 17:45:38
 * @version v1.0
 */
@Service
public class ModelQueryRepositoryImpl  implements ModelQueryRepository{

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

    @Override
	public ParamModelBO getByParamClassName(String paramClassName){

        return null;
    }

    @Override
	public List<ColumnBO> getColumnListByCode(String projectCode, String tableName){

        return null;
    }

    @Override
	public PageBean queryTablePage(PageBean pageBean){
        List<TableModelDO> tableModelDOList = tableModelMapper.getPageList(pageBean);
        pageBean.setRows(TableConvert.INSTANCE.doList2boList(tableModelDOList));
        pageBean.setCount(tableModelMapper.getPageCount(pageBean));
        return pageBean;
    }

    @Override
    public PageBean queryParamPage(PageBean pageBean) {
        List<ParamModelDO> paramModelDOList = paramModelMapper.getPageList(pageBean);
        pageBean.setRows(ParamModelConvert.INSTANCE.doList2boList(paramModelDOList));
        pageBean.setCount(paramModelMapper.getPageCount(pageBean));
        return pageBean;
    }

    @Override
    public TableBO getByTableId(Long id) {
        TableBO tableBO = TableConvert.INSTANCE.do2bo(tableModelMapper.getById(id));
        List<ColumnModelDO> columnModelDOS = columnModelMapper.getByTableId(id);
        tableBO.setColumnList(ColumnConvert.INSTANCE.doList2boList(columnModelDOS));
        return tableBO;
    }

    @Override
    public ParamModelBO getByParamId(Long id) {
        ParamModelDO paramModelDO = paramModelMapper.getById(id);
        ParamModelBO paramModelBO = ParamModelConvert.INSTANCE.do2bo(paramModelDO);
        List<FieldModelDO> fieldModelDOList = fieldModelMapper.getByCode(paramModelDO.getProjectCode(), paramModelDO.getParamClassName());
        paramModelBO.setFieldBeanList(FieldModelConvert.INSTANCE.doList2boList(fieldModelDOList));
        return paramModelBO;
    }

    @Override
    public List<TableBO> searchTable(String content) {
        List<TableModelDO> tableModelDOList = tableModelMapper.search(content);
        return TableConvert.INSTANCE.doList2boList(tableModelDOList);
    }

    @Override
    public List<ParamModelBO> searchParamModel(String content) {
        return ParamModelConvert.INSTANCE.doList2boList(paramModelMapper.search(content));
    }

    @Override
    public List<ModelSuffixConfigBO> getModelSuffixConfigList() {
        List<ModelSuffixConfigDO> modelSuffixConfigDOList = modelSuffixConfigMapper.getAll();
        return ModelSuffixConfigConvert.INSTANCE.doList2boList(modelSuffixConfigDOList);
    }

    @Override
    public PageBean queryModelMappingPage(PageBean pageBean) {
        List<ModelMappingConfigDO> modelMappingConfigDOList = modelMappingConfigMapper.getPageList(pageBean);
        pageBean.setRows(ModelMappingConvert.INSTANCE.doList2boList(modelMappingConfigDOList));
        pageBean.setCount(modelMappingConfigMapper.getPageCount(pageBean));
        return pageBean;
    }

    @Override
    public List<ParamModelBO> getModelByProjectCode(String projectCode) {
        List<ParamModelBO> paramModelBOList = ParamModelConvert.INSTANCE.doList2boList(paramModelMapper.getByProjectCode(projectCode));
        List<TableBO> tableBOList = TableConvert.INSTANCE.doList2boList(tableModelMapper.getByProjectCode(projectCode));
        if(CollectionUtils.isNotEmpty(tableBOList)){
            for (TableBO tableBO : tableBOList){
                ParamModelBO paramModelBO = new ParamModelBO();
                paramModelBO.setProjectCode(projectCode);
                paramModelBO.setParamClassName(tableBO.getTableName());
                paramModelBO.setParamClassDesc(tableBO.getTableComment());
                paramModelBOList.add(paramModelBO);
            }
        }

        return paramModelBOList;
    }

    @Override
    public List<FieldBO> getModelField(String projectCode, String modelName) {

        List<FieldBO> fieldBOList = FieldModelConvert.INSTANCE.doList2boList(fieldModelMapper.getByCode(projectCode,modelName));

        if(CollectionUtils.isNotEmpty(fieldBOList)){
            return fieldBOList;
        }

        fieldBOList = new ArrayList<>();
        List<TableModelDO> tableModelDOList = tableModelMapper.getByProjectCode(projectCode);
        if(CollectionUtils.isNotEmpty(tableModelDOList)){
            Optional<TableModelDO> tableModelDOOptional = tableModelDOList.stream().filter(tableModelDO -> tableModelDO.getTableName().equals(modelName)).findFirst();
            if(tableModelDOOptional.isPresent()){
                List<ColumnBO> columnBOList = ColumnConvert.INSTANCE.doList2boList(columnModelMapper.getByTableId(tableModelDOOptional.get().getId()));
                for (ColumnBO columnBO : columnBOList){
                    FieldBO fieldBO = new FieldBO();
                    fieldBO.setFieldName(columnBO.getColumnName());
                    fieldBO.setFieldDesc(columnBO.getColumnComment());
                    fieldBOList.add(fieldBO);
                }
            }
        }

        return fieldBOList;
    }

    @Override
    public List<ModelMappingBO> getModelMappingListByProjectCode(String projectCode) {
        return ModelMappingConvert.INSTANCE.doList2boList(modelMappingConfigMapper.getListByProjectCode(projectCode));
    }
}