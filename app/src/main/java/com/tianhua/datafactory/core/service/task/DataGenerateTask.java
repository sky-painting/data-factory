package com.tianhua.datafactory.core.service.task;

import com.tianhua.datafactory.client.function.Function;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Description
 * date: 2022/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class DataGenerateTask implements Callable<List<Map<String, Object>>> {
    private Integer start;
    private Integer end;

    private List<DataBuildRequestFieldBO> dataFactoryRequestFieldBeanList;


    private Map<String, Function> functionMap;

    private SecureRandom random = new SecureRandom();
    private FieldValueFactory fieldValueFactory;

    public DataGenerateTask(Integer start, Integer end, List<DataBuildRequestFieldBO> dataFactoryRequestFieldBeanList, Map<String, Function> functionMap, FieldValueFactory fieldValueFactory){
        this.start = start;
        this.end = end;
        this.dataFactoryRequestFieldBeanList = dataFactoryRequestFieldBeanList;
        this.functionMap = functionMap;
        this.fieldValueFactory = fieldValueFactory;
    }



    @Override
    public List<Map<String, Object>> call() throws Exception {
        List<Map<String, Object>> batchList = new ArrayList<>();
        for (int s = this.start * 10000; s < (this.start + 1) * 10000 ; s ++) {
            Map<String, Object> fieldValueMap = new HashMap<>(dataFactoryRequestFieldBeanList.size());
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            dataSourceFieldRequestBean.setCurrentIndex(start);
            //如果有字段依赖可以进行排序
            for (DataBuildRequestFieldBO dataBuildRequestFieldBO : dataFactoryRequestFieldBeanList) {

                dataSourceFieldRequestBean.setFunction(functionMap.get(dataBuildRequestFieldBO.getDataSourceCode()));
                dataSourceFieldRequestBean.setFieldValueMap(fieldValueMap);
                dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
                dataSourceFieldRequestBean.setRandom(random);
                dataSourceFieldRequestBean.setVarDependencyMap(dataBuildRequestFieldBO.getVarDependencyMap());
                //获取随机字段值
                Object fieldValue = null;
                try {
                    fieldValue = fieldValueFactory.getFieldValue(dataSourceFieldRequestBean);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), fieldValue);
            }
            batchList.add(fieldValueMap);
        }
        return batchList;
    }
}
