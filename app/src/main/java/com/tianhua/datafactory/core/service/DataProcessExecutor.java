package com.tianhua.datafactory.core.service;

import com.tianhua.datafactory.core.filter.FilterExecutor;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.domain.ability.GenericService;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * date: 2022/9/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class DataProcessExecutor {
    @Autowired
    private GenericService genericService;

    @Autowired
    private FilterExecutor filterExecutor;
    @Autowired
    private FieldValueFactory fieldValueFactory;

    private static SecureRandom secureRandom = new SecureRandom();



    /**
     *
     * @param dataSourceFieldRequestBean
     * @param fieldBOList
     * @param batchResultList
     * @throws Exception
     */
    public void exeDataGenerateProcess(DataSourceFieldRequestBean dataSourceFieldRequestBean, List<DataBuildRequestFieldBO> fieldBOList,  List<Map<String, Object>> batchResultList) throws Exception {
        Map<String, Object> fieldValueMap = new HashMap<>(fieldBOList.size());

        for (DataBuildRequestFieldBO dataBuildRequestFieldBO : fieldBOList) {
            dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
            dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
            dataSourceFieldRequestBean.setRandom(new SecureRandom());
            dataSourceFieldRequestBean.setVarDependencyMap(dataBuildRequestFieldBO.getVarDependencyMap());

            //1.前置数据过滤
            filterExecutor.exeFilterBefore(dataBuildRequestFieldBO, fieldValueMap, batchResultList);
            //获取随机字段值
            Object fieldValue = fieldValueFactory.getFieldValueWrapper(dataSourceFieldRequestBean);

            fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), fieldValue);
            //2.后置数据过滤
            filterExecutor.exeFilterAfter(dataBuildRequestFieldBO, fieldValueMap, batchResultList);

            List<DataBuildRequestFieldBO> referFieldList = dataBuildRequestFieldBO.getReferFieldList();
            if(CollectionUtils.isEmpty(referFieldList)){
                continue;
            }

            Map<String, Object> referMap = buildReferData(dataBuildRequestFieldBO, batchResultList);
            fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), referMap);

        }
        batchResultList.add(fieldValueMap);
    }


    /**
     * 构建属性为对象类型的引用
     * @param originRequestFieldBO
     * @param batchResultList
     * @return
     * @throws Exception
     */
    private Map<String, Object>  buildReferData(DataBuildRequestFieldBO originRequestFieldBO,  List<Map<String, Object>> batchResultList) throws Exception {
        List<DataBuildRequestFieldBO> referFieldList = originRequestFieldBO.getReferFieldList();

        Map<String, Object> referFieldValueMap = new HashMap<>(referFieldList.size());

        for (DataBuildRequestFieldBO dataBuildRequestFieldBO : referFieldList){
            DataSourceFieldRequestBean referFieldDataSourcdFieldRequestBean = new DataSourceFieldRequestBean();
            referFieldDataSourcdFieldRequestBean.setFieldValueMap(referFieldValueMap);
            referFieldDataSourcdFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
            referFieldDataSourcdFieldRequestBean.setRandom(new SecureRandom());

            if(CollectionUtils.isNotEmpty(dataBuildRequestFieldBO.getReferFieldList())){

                if(JavaFieldTypeEnum.isCollectionType(dataBuildRequestFieldBO.getGenericTypeBO().getWrapType()) && dataBuildRequestFieldBO.getGenericTypeBO().isRealTypeModel()){
                    int randomCount = secureRandom.nextInt(10);
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (int i = 0;i < randomCount ;i++){
                        Map<String, Object> referObjectMap = buildReferData(dataBuildRequestFieldBO, batchResultList);
                        list.add(referObjectMap);
                    }
                    referFieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), genericService.buildGenericData(dataBuildRequestFieldBO.getGenericTypeBO().getWrapType(),list));
                }else {
                    Map<String, Object> referObjectMap = buildReferData(dataBuildRequestFieldBO, batchResultList);
                    referFieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), referObjectMap);
                }

            }else {
                //1.前置数据过滤
                filterExecutor.exeFilterBefore(dataBuildRequestFieldBO, referFieldValueMap, batchResultList);
                //获取随机字段值
                Object referFieldValue = fieldValueFactory.getFieldValueWrapper(referFieldDataSourcdFieldRequestBean);

                referFieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), referFieldValue);

                //2.后置数据过滤
                filterExecutor.exeFilterAfter(dataBuildRequestFieldBO, referFieldValueMap, batchResultList);
            }
        }

        return referFieldValueMap;
    }



}
