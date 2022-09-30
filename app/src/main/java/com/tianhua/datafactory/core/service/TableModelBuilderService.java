package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.FieldExtBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.model.TableBO;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.domain.util.StringHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Description:
 * date: 2022/6/6
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class TableModelBuilderService {

    @Autowired
    private ERPlantUMLParseService erPlantUMLParseService;

    @Autowired
    private ModelRepository modelRepository;

    private Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");


    /**
     * 批量导入
     * @param tableBO
     */
    @Transactional(rollbackFor = Exception.class)
    public void buildERModel(TableBO tableBO){
        try {
            List<String> contentList = FileUtils.readLines(new File(tableBO.getFile()),"UTF-8");
            List<TableBO> tableBOList = erPlantUMLParseService.getPlantUmlContextBean(contentList);


            List<TableBO> oldTableBoList = modelRepository.getDbErByProjectCode(tableBO.getProjectCode());
            Map<String,TableBO> oldTableBOMap = oldTableBoList.stream().collect(Collectors.toMap(TableBO::getTableName, o->o,(k1, k2)->k2));

            List<ParamModelBO> oldParamModelBOList = modelRepository.getModelByProjectCode(tableBO.getProjectCode());

            Map<String,ParamModelBO> oldParamModelMap = oldParamModelBOList.stream().collect(Collectors.toMap(ParamModelBO::getParamClassName,o->o,(k1, k2)->k2));



            for (TableBO table : tableBOList){
                if(oldTableBOMap.containsKey(table.getTableName())){

                    TableBO oldTableBO = oldTableBOMap.get(table.getTableName());
                    oldTableBO.setTableComment(table.getTableComment());
                    oldTableBO.setColumnList(table.getColumnList());
                    modelRepository.updateDBModel(oldTableBO);
                    continue;
                }
                table.init();
                table.setDbName(tableBO.getDbName());
                table.setProjectCode(tableBO.getProjectCode());
                modelRepository.saveDBModel(table);
            }
            //构建javaEntity模型
            if(tableBO.getBuildJavaEntity()){
                for (TableBO tableBO1 : tableBOList) {
                    ParamModelBO paramModelBO = new ParamModelBO();
                    List<FieldBO> fieldBOList = new ArrayList<>();
                    String humpTableName = getHumpTableName(tableBO1.getTableName());
                    String humpClassName = StringHelperUtils.getHumpClassName(humpTableName);
                    paramModelBO.setParamClassName(humpClassName+tableBO.getModelSuffix());
                    paramModelBO.init();
                    paramModelBO.setParamClassDesc(tableBO1.getTableComment());
                    paramModelBO.setProjectCode(tableBO.getProjectCode());
                    paramModelBO.setModuleCode("");
                    paramModelBO.setModelSuffix(tableBO.getModelSuffix());

                    tableBO1.getColumnList().forEach(columnBO -> {
                        FieldBO fieldBO = new FieldBO();
                        String columnFieldName = getHumpTableName(columnBO.getColumnName());
                        fieldBO.setFieldName(columnFieldName);
                        fieldBO.init();
                        String columnTypeName = getColumnTypeName(columnBO.getColumnType());
                        fieldBO.setFieldType(columnTypeName);
                        fieldBO.setFieldExtBO(new FieldExtBO());
                        fieldBO.setProjectCode(tableBO.getProjectCode());
                        fieldBO.setParamClassName(humpClassName);
                        fieldBO.setFieldDoc("");
                        fieldBO.setFieldDesc(columnBO.getColumnComment());
                        fieldBOList.add(fieldBO);
                    });

                    paramModelBO.setFieldBeanList(fieldBOList);

                    if(oldParamModelMap.containsKey(paramModelBO.getParamClassName())){
                        ParamModelBO oldParamModel = oldParamModelMap.get(paramModelBO.getParamClassName());
                        oldParamModel.setFieldBeanList(fieldBOList);
                        modelRepository.updateParamModel(oldParamModel);
                        continue;
                    }

                    modelRepository.saveParamModel(paramModelBO);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取表名对应的变量名
     * eg: user_info->userInfo
     * staff_education_info->staffEducationInfo
     *
     * @param tableName
     * @return
     */
    private String getHumpTableName(String tableName){
        String resultName = "";

        if(!tableName.contains("_")){
            resultName = tableName;
        }else {
            String[] tableNameArr = tableName.split("_");
            int length = tableNameArr.length;
            StringBuilder builder = new StringBuilder();

            if(isNum(tableNameArr[length - 1])){
                if(length == 2){
                    resultName = tableNameArr[0];
                }else {
                    builder.append(tableNameArr[0]);
                    for (int i = 1;i < length - 1;i++){
                        String tag = tableNameArr[i].substring(0,1).toUpperCase().concat(tableNameArr[i].substring(1));
                        builder.append(tag);
                    }
                    resultName = builder.toString();
                }
            }else {
                builder.append(tableNameArr[0]);
                for (int i = 1;i < length;i++){
                    String tag = tableNameArr[i].substring(0,1).toUpperCase().concat(tableNameArr[i].substring(1));
                    builder.append(tag);
                }
                resultName = builder.toString();
            }

        }
        return resultName;
    }


    private boolean isNum(String str){
        return pattern.matcher(str).matches();
    }

    /**
     * 获取字段类型对应的javaentity的java类型
     * @param columnType
     * @return
     */
    private String getColumnTypeName(String columnType){
        if(columnType.toLowerCase().contains("blob") || columnType.toLowerCase().contains("text")){
            return "String";
        }
        else if(columnType.contains("bigint")){
            return "Long";
        }
        else if(columnType.contains("int")){
            return "Integer";
        }
        else if(columnType.contains("varchar")){
            return "String";
        }
        else if(columnType.contains("boolean")){
            return "Boolean";
        }
        else if(columnType.contains("date")){
            return "Date";
        }
        else if(columnType.contains("double")){
            return "Double";
        }
        else if(columnType.contains("timestamp")){
            return "Date";
        }
        else if(columnType.contains("time")){
            return "Date";
        }
        else if(columnType.toUpperCase().contains("DECIMAL")){
            return "BigDecimal";
        }else if(columnType.contains("json")){
            return "String";
        }
        return null;
    }

}
