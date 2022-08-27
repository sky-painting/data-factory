package com.tianhua.datafactory.core.service.liteflow;


import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.factory.FieldRuleDslFactory;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description
 * 在批量数据构建的编排任务中，首先对请求数据进行一定的预处理
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@LiteflowComponent(id = "dataPreProcessingCmp", name = "组装数据")
@Slf4j
public class DataPreProcessingCmp extends NodeComponent {


    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;


    @Autowired
    private FieldRuleDslFactory fieldRuleDslFactory;


    @Autowired
    private FunctionFactory functionFactory;


    @Autowired
    private ModelQueryRepository modelQueryRepository;


    @Override
    public void process() throws Exception {

        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        Map<String, Function> functionMap = new HashMap<>();
        dataBuildRequestBO.setFunctionMap(functionMap);


        bindDataSource(dataBuildRequestBO);
        buildFieldDslRuleBO(dataBuildRequestBO);

        List<DataBuildRequestFieldBO> dataFactoryRequestFieldBeanList = dataBuildRequestBO.getFieldBOList();

        for (DataBuildRequestFieldBO dataFactoryRequestFieldBean : dataFactoryRequestFieldBeanList) {
            String dataSourceCode = dataFactoryRequestFieldBean.getDataSourceCode();
            if (StringUtils.isNotBlank(dataSourceCode) && dataBuildRequestBO.getFunctionMap().get(dataSourceCode) == null && dataSourceCode.startsWith(InnerDataSourceCode.DEFAULT_PREFIX)) {
                Function function = functionFactory.createFunction(dataSourceCode);
                functionMap.put(dataSourceCode,function);
            }
        }

        dataBuildRequestBO.setFunctionMap(functionMap);


    }

    /**
     * 获取数据源详情
     * @param dataBuildRequestBO
     */
    private void bindDataSource(DataBuildRequestBO dataBuildRequestBO){
        List<DataBuildRequestFieldBO> dataBuildRequestFieldBeans = dataBuildRequestBO.getFieldBOList();
        for (DataBuildRequestFieldBO dataBuildRequestFieldBO  : dataBuildRequestFieldBeans){
            if(StringUtils.isEmpty(dataBuildRequestFieldBO.getDataSourceCode())){
                continue;
            }
            DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataBuildRequestFieldBO.getDataSourceCode());
            dataBuildRequestFieldBO.setDataSourceType(dataSourceBO.getSourceType());
            dataBuildRequestFieldBO.setDataSourceBO(dataSourceBO);
        }
    }

    /**
     * 根据dsl中的关系重新排列依赖关系
     * @param dataBuildRequestBO
     */
    private void buildFieldDslRuleBO(DataBuildRequestBO dataBuildRequestBO){
        List<DataBuildRequestFieldBO> dataBuildRequestFieldBOS = dataBuildRequestBO.getFieldBOList();
        Map<String,DataBuildRequestFieldBO> dataBuildRequestFieldBOMap = dataBuildRequestFieldBOS.stream().collect(Collectors.toMap(DataBuildRequestFieldBO::getFieldName, o->o));

        List<DataBuildRequestFieldBO> newFieldBOList = new ArrayList<>();

        Map<String,String> relationMap = new HashMap<>();

        List<ModelSuffixConfigBO>  modelSuffixConfigBOList = modelQueryRepository.getModelSuffixConfigList();

        for (DataBuildRequestFieldBO dataBuildRequestFieldBO  : dataBuildRequestFieldBOS){
            if(StringUtils.isEmpty(dataBuildRequestFieldBO.getBuildRuleDSL())){
                newFieldBOList.add(dataBuildRequestFieldBO);
                continue;
            }

            if(checkModelClass(dataBuildRequestFieldBO.getFieldType(),modelSuffixConfigBOList)){
                List<DataBuildRequestFieldBO> referList = fieldRuleDslFactory.buildReferFieldBO(dataBuildRequestFieldBO, dataBuildRequestBO.getProjectCode());
                referList.stream().forEach(requestFieldBO -> {
                    String dataSourceCode = requestFieldBO.getDataSourceCode();
                    if(StringUtils.isNotEmpty(dataSourceCode) && dataBuildRequestBO.getFunctionMap().get(dataSourceCode) == null){
                        Function function = functionFactory.createFunction(dataSourceCode);
                        dataBuildRequestBO.getFunctionMap().put(dataSourceCode, function);
                    }
                });


                dataBuildRequestFieldBO.setReferFieldList(referList);
                continue;
            }

            DataBuildRequestFieldRuleBO dataBuildRequestFieldRuleBO = fieldRuleDslFactory.buildRuleBO(dataBuildRequestFieldBO.getBuildRuleDSL());
            dataBuildRequestFieldBO.setDataBuildRequestFieldRuleBO(dataBuildRequestFieldRuleBO);

            if(StringUtils.isNotEmpty(dataBuildRequestFieldRuleBO.getRelyField())){
                relationMap.put(dataBuildRequestFieldBO.getFieldName(),dataBuildRequestFieldRuleBO.getRelyField());
            }
        }


        for (Map.Entry<String,String> entry : relationMap.entrySet()){
            String relyField = entry.getValue();
            String relyFieldTmp = relyField;
            Set<String> relyFieldSet = new HashSet<>();
            while (!StringUtils.isEmpty(relyFieldTmp)){
                relyFieldSet.add(relyFieldTmp);
                relyFieldTmp = relationMap.get(relyFieldTmp);
                //循环引用检测
                if(relyFieldSet.contains(relyFieldTmp)){
                    log.warn("存在属性循环引用,请检查field DSL内容。");
                    break;
                }
                String finalRelyFieldTmp = relyFieldTmp;
                Optional<DataBuildRequestFieldBO> optionalDataBuildRequestFieldBO = newFieldBOList.stream()
                        .filter(dataBuildRequestFieldBO -> dataBuildRequestFieldBO.getFieldName().equals(finalRelyFieldTmp))
                        .findAny();

                if(StringUtils.isNotEmpty(relyFieldTmp) && !optionalDataBuildRequestFieldBO.isPresent()){
                    DataBuildRequestFieldBO dataBuildRequestFieldBO = dataBuildRequestFieldBOMap.get(relyField);
                    newFieldBOList.add(dataBuildRequestFieldBO);
                }
            }

            Optional<DataBuildRequestFieldBO> optionalDataBuildRequestFieldBO = newFieldBOList.stream()
                    .filter(dataBuildRequestFieldBO -> dataBuildRequestFieldBO.getFieldName().equals(relyField))
                    .findAny();

            if(!optionalDataBuildRequestFieldBO.isPresent()){
                DataBuildRequestFieldBO dataBuildRequestFieldBO = dataBuildRequestFieldBOMap.get(entry.getKey());
                newFieldBOList.add(dataBuildRequestFieldBO);
            }
        }

        log.info("dataBuildRequestFieldBOS ======= "+ JSON.toJSONString(dataBuildRequestFieldBOS));
    }


    /**
     * 检查属性是否是模型属性
     * @param fieldType
     * @param modelSuffixConfigBOList
     * @return
     */
    private boolean checkModelClass(String fieldType,List<ModelSuffixConfigBO> modelSuffixConfigBOList){

        if(CollectionUtils.isEmpty(modelSuffixConfigBOList)){
            return false;
        }

        Optional<ModelSuffixConfigBO> optional = modelSuffixConfigBOList.stream().filter(modelSuffixConfigBO -> fieldType.endsWith(modelSuffixConfigBO.getSuffix())).findAny();
        if(optional.isPresent()){
            return true;
        }
        return false;
    }


}
