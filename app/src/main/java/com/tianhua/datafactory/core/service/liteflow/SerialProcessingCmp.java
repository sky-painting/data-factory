package com.tianhua.datafactory.core.service.liteflow;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.client.utils.SpringContextUtil;
import com.tianhua.datafactory.core.filter.FilterExecutor;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.ability.GenericService;
import com.tianhua.datafactory.domain.bo.datafactory.*;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * 串行执行数据构建
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@LiteflowComponent(id = "serialProcessingCmp", name = "串行执行数据构建任务")
@Slf4j
public class SerialProcessingCmp extends NodeComponent {
    @Autowired
    private FieldValueFactory fieldValueFactory;

    @Autowired
    private FilterExecutor filterExecutor;

    @Autowired
    private GenericService genericService;

    private static SecureRandom secureRandom = new SecureRandom();

    @Override
    public void process() throws Exception {
        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        List<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount());

        for (int i = 0; i < dataBuildRequestBO.getBuildCount(); i ++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataBuildRequestBO.getFieldBOList().size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            dataSourceFieldRequestBean.setCurrentIndex(i);

            for (DataBuildRequestFieldBO dataBuildRequestFieldBO : dataBuildRequestBO.getFieldBOList()) {
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

        DataBuildResponseBO dataBuildResponseBO = this.getFirstContextBean();
        dataBuildResponseBO.setDataBuildRequestBO(dataBuildRequestBO);
        dataBuildResponseBO.setResultList(batchResultList);
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
