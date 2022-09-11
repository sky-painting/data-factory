package com.tianhua.datafactory.domain.factory;

import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datafactory.FieldDSLKeyConstant;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description
 *
 * 属性规则dsl解析工厂
 *
 * date: 2022/8/12
 * orderCode:
 *   prefix=BRAND_;
 *   subfix=000x;
 *
 * orderDetailBOList:
 *   orderCode.relyField=orderCode
 *   itemId.relySourceCode=com.item.skuFacade(10)#skuid (依赖商品服务的skuid)
 *   count.relySourceCode=com.datafactory.random(10) (随机数函数,10以内)
 *   price.relySourceCode=com.datafactory.random(1000) (随机数函数,1000以内)
 *   relyCount=100 (随机生成100条orderDetailBO使用)
 *   funcVar
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class FieldRuleDslFactory {


    @Autowired
    private ModelQueryRepository modelQueryRepository;

    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;

    /**
     * 根据属性dsl规则解析成规则对象
     * @param fieldBuildRuleDSL
     * @return
     */
    public DataBuildRequestFieldRuleBO buildRuleBO(String fieldBuildRuleDSL){
        DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = new DataBuildRequestFieldRuleBO();
        if(StringUtils.isEmpty(fieldBuildRuleDSL)){
            return dataBuildRequestFieldRuleBO;
        }
        String [] array = fieldBuildRuleDSL.trim().split(";");
        for (String dslStr : array){
            String [] kvArr = dslStr.trim().split("=");

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.PREFIX)){
                dataBuildRequestFieldRuleBO.setPrefix(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.SUBFIX)){
                dataBuildRequestFieldRuleBO.setSubfix(kvArr[1].trim());
            }
            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_LIST_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyListField(kvArr[1].trim());
            }


            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_SET_FIELD)){
                dataBuildRequestFieldRuleBO.setRelySetField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_COUNT)){
                dataBuildRequestFieldRuleBO.setRelyCount(Integer.valueOf(kvArr[1].trim()));
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_MAP_KEY_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyMapKeyField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_MAP_VALUE_FIELD)){
                dataBuildRequestFieldRuleBO.setRelyMapValueField(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.FUNC_VAR)){
                dataBuildRequestFieldRuleBO.setFuncVar(kvArr[1].trim());
            }

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_SOURCE_CODE)){
                dataBuildRequestFieldRuleBO.setRelySourceCode(kvArr[1].trim());
            }
        }

        return dataBuildRequestFieldRuleBO;
    }

    /**
     * 根据dsl文件构建对象类型的引用关系
     * @param dataBuildRequestFieldBO
     * @param projectCode
     * @return
     */
    public List<DataBuildRequestFieldBO> buildReferFieldBOFromDsl(DataBuildRequestFieldBO dataBuildRequestFieldBO, String projectCode){

        String [] array = dataBuildRequestFieldBO.getBuildRuleDSL().trim().split(";");

        List<FieldBO> fieldBOList = modelQueryRepository.getModelField(projectCode, dataBuildRequestFieldBO.getRealFieldType());
        Map<String,FieldBO> fieldBOMap = fieldBOList.stream().collect(Collectors.toMap(FieldBO::getFieldName,o->o));

        Map<String, DataBuildRequestFieldBO> dataBuildRequestFieldBOMap = new HashMap<>();

        for (String dslStr : array){
            String [] kvArr = dslStr.trim().split("=");
            String [] fieldArr = kvArr[0].split("\\.");

            if(kvArr[0].trim().equals(FieldDSLKeyConstant.RELY_COUNT)){
                if(dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO() == null){
                    dataBuildRequestFieldBO.setDataBuildRequestFieldRuleBO(new DataBuildRequestFieldRuleBO());
                }
                dataBuildRequestFieldBO.getDataBuildRequestFieldRuleBO().setRelyCount(Integer.valueOf(kvArr[1].trim()));
                continue;

            }


            FieldBO fieldBO = fieldBOMap.get(fieldArr[0]);

            if(fieldBO == null){
                log.warn("属性不存在,请检查dsl内容.....dslStr : {}",dslStr);
                continue;
            }

            DataBuildRequestFieldBO referRequestFieldBO = dataBuildRequestFieldBOMap.getOrDefault(fieldArr[0],new DataBuildRequestFieldBO<>());

            referRequestFieldBO.setFieldName(fieldBO.getFieldName());
            referRequestFieldBO.setFieldType(fieldBO.getFieldType());
            referRequestFieldBO.setOriginFieldName(dataBuildRequestFieldBO.getFieldName());


            if(referRequestFieldBO.getDataBuildRequestFieldRuleBO() == null){
                referRequestFieldBO.setDataBuildRequestFieldRuleBO(new DataBuildRequestFieldRuleBO());
            }

            DataBuildRequestFieldRuleBO referRequestFieldRuleBO = referRequestFieldBO.getDataBuildRequestFieldRuleBO();

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.PREFIX)){
                referRequestFieldRuleBO.setPrefix(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.SUBFIX)){
                referRequestFieldRuleBO.setSubfix(kvArr[1].trim());
            }
            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_FIELD)){
                referRequestFieldRuleBO.setRelyField(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_LIST_FIELD)){
                referRequestFieldRuleBO.setRelyListField(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_SET_FIELD)){
                referRequestFieldRuleBO.setRelySetField(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_COUNT)){
                referRequestFieldRuleBO.setRelyCount(Integer.valueOf(kvArr[1].trim()));
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_MAP_KEY_FIELD)){
                referRequestFieldRuleBO.setRelyMapKeyField(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_MAP_VALUE_FIELD)){
                referRequestFieldRuleBO.setRelyMapValueField(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.FUNC_VAR)){
                referRequestFieldRuleBO.setFuncVar(kvArr[1].trim());
            }

            if(fieldArr[1].trim().equals(FieldDSLKeyConstant.RELY_SOURCE_CODE)){
                if( kvArr[1].contains("(")){
                    String dataSourceCode = kvArr[1].trim().split("\\(")[0];
                    String funcVar = kvArr[1].trim().split("\\(")[1].replace(")","");
                    if(funcVar.contains("#")){
                        funcVar = funcVar.split("#")[0];
                        referRequestFieldRuleBO.setRelyField(funcVar.split("#")[1]);
                    }
                    referRequestFieldRuleBO.setRelySourceCode(dataSourceCode);
                    referRequestFieldBO.setDataSourceCode(dataSourceCode);
                    referRequestFieldRuleBO.setFuncVar(funcVar);
                }else {
                    referRequestFieldRuleBO.setRelySourceCode(kvArr[1]);
                    referRequestFieldBO.setDataSourceCode(kvArr[1]);
                }

                DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(referRequestFieldBO.getDataSourceCode());
                if(dataSourceBO == null){
                    log.error("根据数据源查不到对应的数据源对象,dataSourceCode = {}",referRequestFieldBO.getDataSourceCode());
                    referRequestFieldBO.setDataSourceType(DataSourceTypeEnum.UN_KNOWN.getCode());
                    continue;
                }

                referRequestFieldBO.setDataSourceType(dataSourceBO.getSourceType());
            }

            referRequestFieldBO.setDataBuildRequestFieldRuleBO(referRequestFieldRuleBO);

            dataBuildRequestFieldBOMap.put(fieldArr[0], referRequestFieldBO);
        }

        return dataBuildRequestFieldBOMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 根据配置好的对象类型的引用关系
     * @param dataBuildRequestFieldBO
     * @param projectCode
     * @return
     */
    public List<DataBuildRequestFieldBO> buildReferFieldBOFromDB(DataBuildRequestFieldBO dataBuildRequestFieldBO, String projectCode){

        List<FieldBO> fieldBOList = modelQueryRepository.getModelField(projectCode, dataBuildRequestFieldBO.getRealFieldType());

        Map<String, DataBuildRequestFieldBO> dataBuildRequestFieldBOMap = new HashMap<>();

        for (FieldBO fieldBO : fieldBOList){

            DataBuildRequestFieldBO referRequestFieldBO = dataBuildRequestFieldBOMap.getOrDefault(fieldBO.getFieldName(),new DataBuildRequestFieldBO<>());

            referRequestFieldBO.setFieldName(fieldBO.getFieldName());
            referRequestFieldBO.setFieldType(fieldBO.getFieldType());
            referRequestFieldBO.setOriginFieldName(dataBuildRequestFieldBO.getFieldName());

            if(referRequestFieldBO.getDataBuildRequestFieldRuleBO() == null){
                referRequestFieldBO.setDataBuildRequestFieldRuleBO(new DataBuildRequestFieldRuleBO());
            }

            DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(fieldBO.getFieldExtBO().getDataSourceCode());
            if(dataSourceBO == null){
                log.error("根据数据源查不到对应的数据源对象,dataSourceCode = {}, fieldName = {}, fieldType = {}",referRequestFieldBO.getDataSourceCode(),fieldBO.getFieldName(),fieldBO.getFieldType());
                referRequestFieldBO.setDataSourceType(DataSourceTypeEnum.UN_KNOWN.getCode());
                continue;
            }

            referRequestFieldBO.setDataSourceCode(fieldBO.getFieldExtBO().getDataSourceCode());
            referRequestFieldBO.setDataSourceType(dataSourceBO.getSourceType());
            referRequestFieldBO.setDataSourceBO(dataSourceBO);
            dataBuildRequestFieldBOMap.put(fieldBO.getFieldName(), referRequestFieldBO);
        }

        return dataBuildRequestFieldBOMap.values().stream().collect(Collectors.toList());
    }


}
