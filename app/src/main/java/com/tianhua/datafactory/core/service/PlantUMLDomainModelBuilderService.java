package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.ability.ReadDomainPlantUMLDocService;
import com.tianhua.datafactory.domain.bo.*;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.FieldExtBO;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;
import com.tianhua.datafactory.domain.enums.*;
import com.tianhua.datafactory.domain.repository.*;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.infrast.dao.dataobject.DataSourceDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
public class PlantUMLDomainModelBuilderService {


    @Autowired
    private ReadDomainPlantUMLDocService readDomainPlantUMLDocService;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelQueryRepository modelQueryRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;

    /**
     * 通过导入构建领域模型数据
     * @param projectCode
     * @param plantUMLFilePath
     */
    @Transactional(rollbackFor = Exception.class)
    public void initPlantUMlDomainModel(String projectCode,String plantUMLFilePath){
        PlantUmlDomainContextBean plantUmlDomainContextBean = readDomainPlantUMLDocService.getPlantUmlContextBean(plantUMLFilePath);
        List<ModelSuffixConfigBO> modelSuffixConfigBOList = modelQueryRepository.getModelSuffixConfigList();
        Set<String> modelSuffixSet = modelSuffixConfigBOList.stream().map(ModelSuffixConfigBO::getSuffix).collect(Collectors.toSet());
        for (Map.Entry<String,ClassBean> classBeanEntry : plantUmlDomainContextBean.getClassBeanMap().entrySet()){
            Optional<String> suffixOptional = modelSuffixSet.stream().filter(suffix->classBeanEntry.getKey().toLowerCase().endsWith(suffix.toLowerCase())).findFirst();
            //业务模型类
            if(suffixOptional.isPresent()){
                ParamModelBO paramModelBO = ParamModelBO.getInstance(classBeanEntry.getKey());
                paramModelBO.init();
                paramModelBO.setProjectCode(projectCode);
                paramModelBO.setModuleCode("");
                paramModelBO.setParamClassDesc(classBeanEntry.getValue().getClassDesc());
                paramModelBO.setModelSuffix(suffixOptional.get());
                List<FieldBO> fieldBOList = classBeanEntry.getValue().getFieldBeanList();
                fieldBOList.stream().forEach(fieldBO -> {
                    fieldBO.setFieldExtBO(new FieldExtBO());
                    fieldBO.setProjectCode(projectCode);
                    fieldBO.setFieldDoc("");
                });
                paramModelBO.setFieldBeanList(fieldBOList);
                modelRepository.saveParamModel(paramModelBO);
            }else {
                //业务服务api类
                buildApiBatch(projectCode,classBeanEntry.getKey(),classBeanEntry.getValue().getMethodBeanList());
            }
        }

        for (Map.Entry<String, InterfaceBean> interfaceBeanEntry : plantUmlDomainContextBean.getInterfaceBeanMap().entrySet()){
            InterfaceBean interfaceBean = interfaceBeanEntry.getValue();
            if(CollectionUtils.isEmpty(interfaceBean.getMethodBeanList())){
                continue;
            }
            buildApiBatch(projectCode,interfaceBeanEntry.getKey(),interfaceBeanEntry.getValue().getMethodBeanList());
        }



        //注册枚举数据源
        registEnumDataSource(projectCode,plantUmlDomainContextBean);


    }

    /**
     * 批量构建api
     * @param projectCode
     * @param className
     * @param methodBeanList
     */
    private void  buildApiBatch(String projectCode, String className,List<MethodBean> methodBeanList){
        //业务服务api类
        for (MethodBean methodBean :  methodBeanList){
            ApiBO apiBO = ApiBO.getInstance(projectCode,className+"."+methodBean.getSimplMethodName());
            apiBO.setApiType(ApiTypeEnum.SERVICE_API.getType());
            apiBO.init();
            apiBO.enable();
            apiBO.setApiDoc(methodBean.getDesc());
            if(methodBean.getParamArr() !=null){
                List<ParamModelBO> paramModelBOList = Lists.newArrayList();
                for (String param : methodBean.getParamArr()){
                    String [] paramArr = param.split(" ");
                    ParamModelBO paramModelBO = ParamModelBO.getInstance(paramArr[0]);
                    paramModelBO.setModelSuffix("");
                    paramModelBO.setProjectCode(projectCode);
                    paramModelBO.setParamClassDesc("");
                    paramModelBO.init();
                    paramModelBO.setModuleCode("");
                    paramModelBOList.add(paramModelBO);
                }

                apiBO.setParamList(paramModelBOList);
                apiBO.setReturnValue("");
                apiBO.setMethodType(MethodTypeEnum.INNER_SERVICE.getType());
                ProjectBO projectBO = ProjectBO.getInstance();
                projectBO.addApiBo(apiBO);
                projectRepository.saveProject(projectBO);
            }
        }
    }

    /**
     * 注册枚举数据源
     * @param projectCode
     * @param plantUmlDomainContextBean
     */
    private void registEnumDataSource(String projectCode, PlantUmlDomainContextBean plantUmlDomainContextBean){
        //注册枚举数据源
        Map<String, EnumBean> enumBeanMap = plantUmlDomainContextBean.getEnumBeanMap();
        if (enumBeanMap == null || enumBeanMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, EnumBean> enumBeanEntry : enumBeanMap.entrySet()) {
            EnumBean enumBean = enumBeanEntry.getValue();
            DataSourceBO dataSourceBO = new DataSourceBO();
            String sourceCode = projectCode+":"+ enumBean.getClassName();
            dataSourceBO.setSourceCode(sourceCode);
            dataSourceBO.setSourceName(enumBean.getClassDesc());
            dataSourceBO.setSourceType(DataSourceTypeEnum.FROM_SERVICE_ENUM.getCode());
            dataSourceBO.setProviderService(projectCode);
            dataSourceBO.setProviderDomainUrl("");
            dataSourceBO.setRegistServer(RegistServerEnum.INDEPENDENCY_SERVICE.getCode());
            dataSourceBO.setStructType("");
            dataSourceBO.setVisitStrategy(VisitStrategyEnums.LOCAL_CACHE.getCode());
            dataSourceBO.setUrl(sourceCode);
            List<Map<String, String>> valueList = enumBean.getEnumValueList();
            List<KVPairBO> kvPairBOList = new ArrayList<>();
            for (Map<String, String> valueMap : valueList) {
                valueMap.forEach((k, v) -> {
                    KVPairBO kvPairBO;
                    if(!k.contains(" ")){
                        kvPairBO = KVPairBO.instance(k, v);
                    }else {
                        kvPairBO = KVPairBO.instance(k.split(" ")[1], v);
                    }
                    kvPairBO.setGroupKey(sourceCode);
                    kvPairBO.setParentKey(projectCode);
                    kvPairBOList.add(kvPairBO);
                });
            }
            dataSourceBO.setKvPairList(kvPairBOList);

            DataSourceBO old = dataSourceQueryRepository.getByDataSourceCode(dataSourceBO.getSourceCode());
            if(old != null){
                continue;
            }
            //将服务枚举数据当作数据源进行注册，同时枚举值当作数据源的值进行管理
            dataSourceRepository.regist(dataSourceBO);
        }
    }

}
