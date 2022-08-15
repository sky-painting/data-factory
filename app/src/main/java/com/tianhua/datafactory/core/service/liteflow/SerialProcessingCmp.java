package com.tianhua.datafactory.core.service.liteflow;

import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildResponseBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
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
@LiteflowComponent(id = "serialProcessingCmp", name = "单线程执行数据构建任务")
public class SerialProcessingCmp extends NodeComponent {
    @Autowired
    private FieldValueFactory fieldValueFactory;


    @Override
    public void process() throws Exception {
        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        ArrayList<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount());

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
                Object fieldValue = fieldValueFactory.getFieldValue(dataSourceFieldRequestBean);
                fieldValueMap.put(dataBuildRequestFieldBO.getFieldName(), fieldValue);
            }
            batchResultList.add(fieldValueMap);
        }

        DataBuildResponseBO dataBuildResponseBO = this.getFirstContextBean();
        dataBuildResponseBO.setDataBuildRequestBO(dataBuildRequestBO);
        dataBuildResponseBO.setResultList(batchResultList);

    }
}
