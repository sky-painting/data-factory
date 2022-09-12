package com.tianhua.datafactory.core.service.liteflow;


import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import com.tianhua.datafactory.domain.ability.GenericService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldRuleBO;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.factory.FieldRuleDslFactory;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
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
@LiteflowComponent(id = "dataPreProcessingCmp", name = "数据构建预处理")
@Slf4j
public class DataPreProcessingCmp extends NodeComponent {

    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;


    @Autowired
    private FieldRuleDslFactory fieldRuleDslFactory;


    @Autowired
    private FunctionFactory functionFactory;



    @Autowired
    private GenericService genericService;

    @Override
    public void process() throws Exception {

        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();

        //1.构建数据源
        bindDataSource(dataBuildRequestBO);
        //2.解析构建属性dsl信息
        buildFieldDslRuleBO(dataBuildRequestBO);
    }

    /**
     * 获取数据源详情
     * @param dataBuildRequestBO
     */
    private void bindDataSource(DataBuildRequestBO dataBuildRequestBO){
        List<DataBuildRequestFieldBO> dataBuildRequestFieldBeans = dataBuildRequestBO.getFieldBOList();
        for (DataBuildRequestFieldBO dataBuildRequestFieldBO  : dataBuildRequestFieldBeans){
            if(StringUtils.isEmpty(dataBuildRequestFieldBO.getDataSourceCode())){
                log.warn("当前属性没有绑定数据源,无法生成对应数据值,fieldName = {}, apiSign = {}, paramModelCode = {}",dataBuildRequestFieldBO.getFieldName(),dataBuildRequestBO.getApiSign(),dataBuildRequestBO.getParamModelCode());
                continue;
            }
            DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataBuildRequestFieldBO.getDataSourceCode());
            if(dataSourceBO == null){
                log.error("当前属性已绑定数据源,但找不到对应的数据源信息,fieldName = {},  dataSourceCode = {}",dataBuildRequestFieldBO.getFieldName(), dataBuildRequestFieldBO.getDataSourceCode());
                continue;
            }
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


        for (DataBuildRequestFieldBO dataBuildRequestFieldBO  : dataBuildRequestFieldBOS){
            boolean isModelClassRefer = genericService.checkModelClass(dataBuildRequestFieldBO);
            //普通数据类型，同时dsl为空
            if(StringUtils.isEmpty(dataBuildRequestFieldBO.getBuildRuleDSL()) && !isModelClassRefer){
                newFieldBOList.add(dataBuildRequestFieldBO);
                continue;
            }
            List<DataBuildRequestFieldBO> referList = null;
            //普通数据类型，同时dsl为空
            if(StringUtils.isEmpty(dataBuildRequestFieldBO.getBuildRuleDSL()) && isModelClassRefer){
                referList = fieldRuleDslFactory.buildReferFieldBOFromDB(dataBuildRequestFieldBO, dataBuildRequestBO.getProjectCode());
            }

            if(StringUtils.isNotEmpty(dataBuildRequestFieldBO.getBuildRuleDSL()) && isModelClassRefer ){
                referList = fieldRuleDslFactory.buildReferFieldBOFromDsl(dataBuildRequestFieldBO, dataBuildRequestBO.getProjectCode());
                Map<String, DataBuildRequestFieldBO> fieldBOMap = referList.stream().collect(Collectors.toMap(DataBuildRequestFieldBO::getFieldName, o->o));

                List<DataBuildRequestFieldBO> originReferList = fieldRuleDslFactory.buildReferFieldBOFromDB(dataBuildRequestFieldBO, dataBuildRequestBO.getProjectCode());
                originReferList.stream().forEach(dataBuildRequestFieldBO1 -> {
                    DataBuildRequestFieldBO dslBO = fieldBOMap.get(dataBuildRequestFieldBO1.getFieldName());
                    if(dslBO != null){
                        dataBuildRequestFieldBO1.setDataSourceCode(dslBO.getDataSourceCode());
                        dataBuildRequestFieldBO1.setDefaultValueList(dslBO.getDefaultValueList());
                    }
                });
                referList = originReferList;
            }

            if(CollectionUtils.isNotEmpty(referList)){
                dataBuildRequestFieldBO.setReferFieldList(referList);
                newFieldBOList.add(dataBuildRequestFieldBO);
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


}
