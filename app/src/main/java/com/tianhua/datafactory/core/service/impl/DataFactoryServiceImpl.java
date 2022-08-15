package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.client.constants.InnerDataSourceCode;
import com.tianhua.datafactory.client.function.Function;

import com.tianhua.datafactory.client.function.factory.FunctionFactory;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.core.service.DataGenerateService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.core.service.task.DataGenerateTask;
import com.tianhua.datafactory.domain.GlobalConstant;
import com.tianhua.datafactory.domain.bo.datafactory.*;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.enums.DataSourceTypeEnum;
import com.tianhua.datafactory.domain.factory.FieldRuleDslFactory;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * description: DataFactoryServiceImpl <br>
 * date: 2020/12/5 23:40 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class DataFactoryServiceImpl implements DataFactoryService {

    private ThreadLocal<SecureRandom> randomThreadLocal = new ThreadLocal<>();



    @Autowired
    private FieldValueFactory fieldValueFactory;


    @Autowired
    private DataSourceQueryRepository dataSourceQueryRepository;


    @Autowired
    private FieldRuleDslFactory fieldRuleDslFactory;

    @Resource
    private FlowExecutor flowExecutor;


    @Override
    public ResultDataDto<List<Map<String, Object>>> generateData(DataBuildRequestBO dataBuildRequestBO) throws Exception {
        randomThreadLocal.set(new SecureRandom());

        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp(GlobalConstant.CHAIN_FLOW, dataBuildRequestBO, DataBuildResponseBO.class);

        ResultDataDto resultDataDto = new ResultDataDto();

        DataBuildResponseBO context = liteflowResponse.getContextBean(DataBuildResponseBO.class);

        int coreNum = Runtime.getRuntime().availableProcessors();
        log.info("coreNum ============================ "+coreNum);
        resultDataDto.setData(context.getResultList());

        return resultDataDto;
    }

    @Override
    public String buildData(DataBuildRequestFieldBO dataBuildRequestFieldBO) {
        DataSourceFieldRequestBean dataSourceFieldRequestBean = new DataSourceFieldRequestBean();
        dataSourceFieldRequestBean.setFunction(dataBuildRequestFieldBO.getFunction());

        String dataSourceCode = dataBuildRequestFieldBO.getDataSourceCode();
        if(dataSourceCode.contains("#")){
            dataSourceCode = dataSourceCode.split("#")[0];
        }
        DataSourceBO dataSourceBO = dataSourceQueryRepository.getByDataSourceCode(dataSourceCode);
        dataBuildRequestFieldBO.setDataSourceType(dataSourceBO.getSourceType());
        dataBuildRequestFieldBO.setDataSourceBO(dataSourceBO);
        dataSourceFieldRequestBean.setDataBuildRequestFieldBO(dataBuildRequestFieldBO);
        Object object = null;
        try {
            object = fieldValueFactory.getFieldValue(dataSourceFieldRequestBean);
            if(object == null){
                return "";
            }
            return object.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
