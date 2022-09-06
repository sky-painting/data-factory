package com.tianhua.datafactory.core.service.liteflow;

import com.tianhua.datafactory.client.utils.SpringContextUtil;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.domain.ability.DataFilter;
import com.tianhua.datafactory.domain.bo.datafactory.*;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
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
public class SerialProcessingCmp extends NodeComponent {
    @Autowired
    private FieldValueFactory fieldValueFactory;

    @Override
    public void process() throws Exception {
        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        List<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount());

        for (int i = 0; i < dataBuildRequestBO.getBuildCount(); i ++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataBuildRequestBO.getFieldBOList().size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            dataSourceFieldRequestBean.setCurrentIndex(i);
            //如果有字段依赖可以进行排序
            for (DataBuildRequestFieldBO dataBuildRequestFieldBO : dataBuildRequestBO.getFieldBOList()) {

                dataSourceFieldRequestBean.setFunction(dataBuildRequestBO.getFunctionMap().get(dataBuildRequestFieldBO.getDataSourceCode()));
                dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
                dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
                dataSourceFieldRequestBean.setRandom(new SecureRandom());
                dataSourceFieldRequestBean.setVarDependencyMap(dataBuildRequestFieldBO.getVarDependencyMap());
                //获取随机字段值
                exeFilterBefore(dataBuildRequestFieldBO, fieldValueMap, batchResultList);

                Object fieldValue = fieldValueFactory.getFieldValue(dataSourceFieldRequestBean);
                fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), fieldValue);

                exeFilterAfter(dataBuildRequestFieldBO, fieldValueMap, batchResultList);

                List<DataBuildRequestFieldBO> referFieldList = dataBuildRequestFieldBO.getReferFieldList();
                if(CollectionUtils.isEmpty(referFieldList)){
                    continue;
                }
                Map<String, Object> referFieldValueMap = new HashMap<>(referFieldList.size());

                for (DataBuildRequestFieldBO referFieldBO : referFieldList){
                    DataSourceFieldRequestBean referFieldDataSourcdFieldRequestBean = new DataSourceFieldRequestBean();
                    referFieldDataSourcdFieldRequestBean.setFunction(dataBuildRequestBO.getFunctionMap().get(referFieldBO.getDataSourceCode()));
                    referFieldDataSourcdFieldRequestBean.setFieldValueMap(fieldValueMap);
                    referFieldDataSourcdFieldRequestBean.setDataBuildRequestFieldBO(referFieldBO);
                    referFieldDataSourcdFieldRequestBean.setRandom(new SecureRandom());

                    exeFilterBefore(referFieldBO, fieldValueMap, batchResultList);

                    //获取随机字段值
                    Object referFieldValue = fieldValueFactory.getFieldValue(referFieldDataSourcdFieldRequestBean);
                    referFieldValueMap.put(referFieldBO.getFieldName(), referFieldValue);

                    fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), referFieldValueMap);
                    exeFilterAfter(referFieldBO, fieldValueMap, batchResultList);
                }
                fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), referFieldValueMap);
            }
            batchResultList.add(fieldValueMap);
        }

        DataBuildResponseBO dataBuildResponseBO = this.getFirstContextBean();
        dataBuildResponseBO.setDataBuildRequestBO(dataBuildRequestBO);
        dataBuildResponseBO.setResultList(batchResultList);
    }


    /**
     * 在获取变量之前执行
     *
     * @param dataBuildRequestFieldBO
     * @param fieldValueMap
     * @param batchResultList
     */
    private void exeFilterBefore(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> fieldValueMap, List<Map<String, Object>> batchResultList){
        List<DataFilter> dataFilterList = SpringContextUtil.getBeanOfType(DataFilter.class);
        for (DataFilter dataFilter : dataFilterList){
            Order order = dataFilter.getClass().getAnnotation(Order.class);
            if(order.value() < 0){
                dataFilter.dataFilt(dataBuildRequestFieldBO, fieldValueMap, batchResultList);
            }
        }
    }

    /**
     * 在获取变量之后执行
     * @param dataBuildRequestFieldBO
     * @param fieldValueMap
     * @param batchResultList
     */
    private void exeFilterAfter(DataBuildRequestFieldBO dataBuildRequestFieldBO, Map<String, Object> fieldValueMap, List<Map<String, Object>> batchResultList){
        List<DataFilter> dataFilterList = SpringContextUtil.getBeanOfType(DataFilter.class);
        for (DataFilter dataFilter : dataFilterList){
            Order order = dataFilter.getClass().getAnnotation(Order.class);
            if(order.value() > 0){
                dataFilter.dataFilt(dataBuildRequestFieldBO, fieldValueMap, batchResultList);
            }
        }
    }

}
