package com.tianhua.datafactory.core.service.liteflow;

import com.tianhua.datafactory.core.service.DataProcessExecutor;
import com.tianhua.datafactory.domain.bo.datafactory.*;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DataProcessExecutor dataProcessExecutor;

    @Override
    public void process() throws Exception {
        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        List<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount());

        for (int i = 0; i < dataBuildRequestBO.getBuildCount(); i ++) {
            DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
            dataSourceFieldRequestBean.setCurrentIndex(i);
            //内部处理
            dataProcessExecutor.exeDataGenerateProcess(dataSourceFieldRequestBean, dataBuildRequestBO.getFieldBOList(),batchResultList);
        }

        DataBuildResponseBO dataBuildResponseBO = this.getFirstContextBean();
        dataBuildResponseBO.setDataBuildRequestBO(dataBuildRequestBO);
        dataBuildResponseBO.setResultList(batchResultList);
    }

}
