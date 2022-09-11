package com.tianhua.datafactory.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.core.service.DataTypeAdapter;
import com.tianhua.datafactory.core.service.FieldValueFactory;
import com.tianhua.datafactory.core.service.GenericService;
import com.tianhua.datafactory.domain.GlobalConstant;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.*;
import com.tianhua.datafactory.domain.bo.datasource.DataSourceBO;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.repository.DataSourceQueryRepository;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.*;

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

    @Resource
    private FlowExecutor flowExecutor;


    @Autowired
    private ProjectQueryRepository projectQueryRepository;


    @Autowired
    private ModelQueryRepository modelQueryRepository;

    @Autowired
    private GenericService genericService;

    @Resource(name = "basicTypeAdapter")
    private DataTypeAdapter basicTypeAdapter;


    @Override
    public ResultDataDto<List<Map<String, Object>>> generateData(DataBuildRequestBO dataBuildRequestBO) throws Exception {
        randomThreadLocal.set(new SecureRandom());

        //1.进入liteflow工作流
        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp(GlobalConstant.CHAIN_FLOW, dataBuildRequestBO, DataBuildResponseBO.class);

        ResultDataDto resultDataDto = new ResultDataDto();

        DataBuildResponseBO context = liteflowResponse.getContextBean(DataBuildResponseBO.class);
        //2.从工作流中获取结果
        resultDataDto.setData(context.getResultList());
        return resultDataDto;
    }

    @Override
    public ResultDataDto<List<Map<String, Object>>> generateDataApiReqParam(DataBuildRequestBO dataBuildRequestBO) throws Exception {
        randomThreadLocal.set(new SecureRandom());

        //1.进入liteflow工作流
        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp(GlobalConstant.CHAIN_FLOW, dataBuildRequestBO, DataBuildResponseBO.class);

        ResultDataDto resultDataDto = new ResultDataDto();

        DataBuildResponseBO context = liteflowResponse.getContextBean(DataBuildResponseBO.class);
        //2.从工作流中获取结果
        resultDataDto.setData(context.getResultList());
        return resultDataDto;
    }

    @Override
    public ResultDataDto<List<Map<String, Object>>> generateDataApiRespParam(String apiSign) throws Exception {

        ApiBO apiBO = projectQueryRepository.getBySign(apiSign);
        log.info("构建接口mock数据的apiBO = {}", JSON.toJSONString(apiBO));
        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();
        dataBuildRequestBO.setApiSign(apiSign);
        dataBuildRequestBO.setProjectCode(apiBO.getProjectCode());
        if(apiBO.getMockCount() == null || apiBO.getMockCount() == 0){
            dataBuildRequestBO.setBuildCount(1);
        }else {
            dataBuildRequestBO.setBuildCount(apiBO.getMockCount());
        }
        ParamModelBO paramModelBO = apiBO.getReturnParamModel();
        if(paramModelBO == null){
            throw new Exception("接口返回模型为空,请在接口管理中配置返回模型");
        }

        GenericTypeBO genericTypeBO = genericService.getGenericType(paramModelBO.getParamClassName());
        genericTypeBO.setApiBO(apiBO);
        List<DataBuildRequestFieldBO> fieldBOList = basicTypeAdapter.buildFieldList(genericTypeBO);
        dataBuildRequestBO.setFieldBOList(fieldBOList);
        return generateData(dataBuildRequestBO);
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
