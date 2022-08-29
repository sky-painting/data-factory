package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.domain.ability.ReadApiPlantUMLDocService;
import com.tianhua.datafactory.domain.bo.EnumBean;
import com.tianhua.datafactory.domain.bo.PlantUMLApiContextBean;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.FieldExtBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ProjectBO;
import com.tianhua.datafactory.domain.enums.*;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.repository.DataSourceRepository;
import com.tianhua.datafactory.domain.repository.ModelRepository;
import com.tianhua.datafactory.domain.repository.ProjectRepository;
import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class PlantUMLApiModelBuilderService {


    @Autowired
    private ReadApiPlantUMLDocService readApiPlantUMLDocService;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;

    /**
     * 通过导入构建元数据模型
     *
     * @param projectCode
     * @param plantUMLFilePath
     */
    @Transactional(rollbackFor = Exception.class)
    public void initPlantUMlModel(String projectCode, String plantUMLFilePath) {
        PlantUMLApiContextBean plantUMLApiContextBean = readApiPlantUMLDocService.readDoc(plantUMLFilePath);
        for (ParamModelBO paramModelBO : plantUMLApiContextBean.getParamClassBeanList()) {
            paramModelBO.init();
            paramModelBO.setModuleCode("");
            paramModelBO.setModelSuffix("");
            paramModelBO.getFieldBeanList().stream().forEach(fieldBO -> {
                fieldBO.setFieldDoc("");
                fieldBO.setFieldExtBO(new FieldExtBO());
                fieldBO.setProjectCode(projectCode);
                fieldBO.setParamClassName(paramModelBO.getParamClassName());
            });
            paramModelBO.setProjectCode(projectCode);
            modelRepository.saveParamModel(paramModelBO);
        }

        plantUMLApiContextBean.getApiBeanList().stream().forEach(apiBO -> {
            apiBO.enable();
            apiBO.setProjectCode(projectCode);
            if (apiBO.getApiUrl().contains("/")) {
                apiBO.setApiType(ApiTypeEnum.HTTP_API.getType());
            }
            apiBO.setReturnValue("");
            apiBO.buildReturnParamModel();
            apiBO.init();
        });

        //注册枚举数据源
        registEnumDataSource(projectCode,plantUMLApiContextBean);

        ProjectBO projectBO = ProjectBO.getInstance();
        projectBO.setApiList(plantUMLApiContextBean.getApiBeanList());
        projectRepository.saveProject(projectBO);

    }


    /**
     * 注册枚举数据源
     * @param projectCode
     * @param plantUMLApiContextBean
     */
    private void registEnumDataSource(String projectCode, PlantUMLApiContextBean plantUMLApiContextBean){
        //注册枚举数据源
        Map<String, EnumBean> enumBeanMap = plantUMLApiContextBean.getEnumBeanMap();
        if (enumBeanMap == null || enumBeanMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, EnumBean> enumBeanEntry : enumBeanMap.entrySet()) {
            EnumBean enumBean = enumBeanEntry.getValue();
            DataSourceBO dataSourceBO = new DataSourceBO();
            String sourceCode = projectCode+":" + enumBean.getClassName();
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
                    KVPairBO kvPairBO = KVPairBO.instance(k, v);
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
