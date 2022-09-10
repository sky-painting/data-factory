package com.tianhua.datafactory.core.service;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.client.factory.ReturnWrapClassFactory;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * 数据
 * date: 2022/9/5
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ApiMockDataAdapter {
    @Autowired
    private DataFactoryService dataFactoryService;
    @Autowired
    private ReturnWrapClassFactory returnWrapClassFactory;

    @Autowired
    private ProjectQueryRepository projectQueryRepository;


    /**
     * apimock场景接口出参适配
     *
     * @param apiSign     api接口签名
     * @param successData 是否返回成功的数据
     * @return
     * @throws Exception
     */
    public Object getApiMockDataResp(String apiSign, Boolean successData) throws Exception {
        if (successData == null) {
            successData = true;
        }

        Map<String, Object> resultMap = new HashMap<>();
        ApiBO apiBO = projectQueryRepository.getBySign(apiSign);
        if (apiBO == null) {
            throw new Exception("根据apiSign找不到对应的API模型");
        }

        ResultDataDto<List<Map<String, Object>>> randomData = dataFactoryService.generateDataApiRespParam(apiSign);

        if (ReturnWrapClassEnum.isOrigin(apiBO.getApiReturnWrapType())) {
            if (successData) {
                return randomData.getData();
            }
            return null;
        }

        if (!successData) {
            resultMap.put("code", "500");
            resultMap.put("data", null);
            resultMap.put("msg", "失败");
            return resultMap;
        }

        //这里借助平台自己封装的msg,code,data返回,可以通过公共项目空间定义的ResultDTO进行适配修改
        if (ReturnWrapClassEnum.isResultDTO(apiBO.getApiReturnWrapType())) {
            List<Map<String, Object>> randomDataList = randomData.getData();
            if (randomDataList.size() == 1) {
                return returnWrapClassFactory.buildSuccessResultDTO(randomDataList.get(0));
            }
            return returnWrapClassFactory.buildSuccessResultDTO(randomDataList);
        }

        //这里借助平台自己封装的msg,code,data返回,可以通过公共项目空间定义的ResultDTO进行适配修改
        if (ReturnWrapClassEnum.isResultPageDTO(apiBO.getApiReturnWrapType())) {
            List<Map<String, Object>> randomDataList = randomData.getData();
            return returnWrapClassFactory.buildSuccessResultPage(randomDataList);
        }
        return resultMap;
    }


    /**
     * 接口入参
     *
     * @param apiSign
     * @return
     * @throws Exception
     */
    public Object getApiMockDataReq(String apiSign, List<ParamModelBO> paramModelBOList ) throws Exception {

        ApiBO apiBO = projectQueryRepository.getBySign(apiSign);
        if (apiBO == null) {
            throw new Exception("根据apiSign找不到对应的API模型");
        }

        DataBuildRequestBO dataBuildRequestBO = new DataBuildRequestBO();

        dataBuildRequestBO.setBuildCount(1);
        dataBuildRequestBO.setProjectCode(apiBO.getProjectCode());
        dataBuildRequestBO.setApiSign(apiBO.getApiSign());

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();

        if(CollectionUtils.isEmpty(paramModelBOList)){
            for (int i = 0;i < apiBO.getParamList().size();i ++){
                ParamModelBO paramModelBO = apiBO.getParamList().get(i);
                DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
                dataBuildRequestFieldBO.setFieldName(paramModelBO.getParamVarName());
                dataBuildRequestFieldBO.setFieldType(paramModelBO.getParamClassName());
                dataBuildRequestFieldBO.setDataSourceCode(paramModelBO.getDataSourceCode());
                fieldBOList.add(dataBuildRequestFieldBO);
            }
        }else {
            for (int i = 0;i < apiBO.getParamList().size();i ++){
                ParamModelBO paramModelBO = apiBO.getParamList().get(i);
                DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO();
                dataBuildRequestFieldBO.setFieldName(paramModelBOList.get(i).getParamVarName());
                dataBuildRequestFieldBO.setFieldType(paramModelBO.getParamClassName());
                dataBuildRequestFieldBO.setDataSourceCode(paramModelBOList.get(i).getDataSourceCode());
                fieldBOList.add(dataBuildRequestFieldBO);
            }
        }


        dataBuildRequestBO.setFieldBOList(fieldBOList);

        ResultDataDto<List<Map<String, Object>>> randomData = dataFactoryService.generateDataApiReqParam(dataBuildRequestBO);

        return randomData.getData().get(0);
    }

}