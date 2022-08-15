package com.tianhua.datafactory.core.service.liteflow;

import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.core.service.task.DataGenerateTask;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildResponseBO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Description
 * 并行执行数据构建
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@LiteflowComponent(id = "parallelProcessingCmp", name = "线程池执行数据构建任务")
@Slf4j
public class ParallelProcessingCmp  extends NodeComponent {

    @Autowired
    private FieldValueFactory fieldValueFactory;


    @Override
    public void process() throws Exception {

        int coreNum = Runtime.getRuntime().availableProcessors();
        log.info("coreNum ============================ "+coreNum);


        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                4 * coreNum,
                8 * coreNum,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        threadPool.prestartAllCoreThreads();

        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();
        ArrayList<Map<String, Object>> batchResultList = new ArrayList<>(dataBuildRequestBO.getBuildCount());

        int taskCount = dataBuildRequestBO.getBuildCount() / 10000;
        int leftSize = dataBuildRequestBO.getBuildCount() % 10000;
        for ( int i = 0; i < taskCount; i ++) {
            int start = i * 10000;
            int end = ( i + 1 ) * 10000;
            if(leftSize != 0 && i == taskCount - 1){
                end = end + leftSize;
            }
            Future<List<Map<String, Object>>> future = threadPool.submit(new DataGenerateTask(start, end, dataBuildRequestBO.getFieldBOList(), dataBuildRequestBO.getFunctionMap(),fieldValueFactory));
            batchResultList.addAll(future.get());
        }

        DataBuildResponseBO dataBuildResponseBO = this.getContextBean(DataBuildResponseBO.class);
        dataBuildResponseBO.setDataBuildRequestBO(dataBuildRequestBO);
        dataBuildResponseBO.setResultList(batchResultList);
    }
}
