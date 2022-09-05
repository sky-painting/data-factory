package com.tianhua.datafactory.controller.webmock;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.client.factory.ReturnWrapClassFactory;
import com.tianhua.datafactory.convert.ApiConverter;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.repository.ProjectQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class WebMockController {

    @Autowired
    private ProjectQueryRepository projectQueryRepository;


    @Autowired
    private DataFactoryService dataFactoryService;

    @Autowired
    private ReturnWrapClassFactory returnWrapClassFactory;

    /**
     * 前后端接口联调时接口的数据mock返回
     * @return
     */
    @RequestMapping(value = "/apimock",method = RequestMethod.GET)
    public Map<String, Object> getApiResponseData(@RequestParam(value = "apiSign") String apiSign){
        try {
            ResultDataDto<List<Map<String, Object>>> randomData = dataFactoryService.generateDataApiRespParam(apiSign);
            List<Map<String, Object>> randomDataList = randomData.getData();
            if(randomDataList.size() == 1){
                return returnWrapClassFactory.buildSuccessResultDTO(randomDataList.get(0));
            }
            return returnWrapClassFactory.buildSuccessResultDTO(randomDataList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 获取基于项目的后端api签名列表
     * @param projectCode
     * @return
     */
    @RequestMapping(value = "/apisign/{projectCode}",method = RequestMethod.GET)
    public ResultDataDto getApiSginData(@PathVariable(value = "projectCode") String projectCode){
        ResultDataDto resultDataDto = new ResultDataDto<>();
        List<ApiBO> apiBOList = projectQueryRepository.getApiListByCode(projectCode);
        //过滤掉非web接口
        apiBOList = apiBOList.stream().filter(apiBO -> apiBO.getApiSign().contains("/")).collect(Collectors.toList());

        resultDataDto.setData(ApiConverter.INSTANCE.BOs2VOs(apiBOList));
        return resultDataDto;
    }


}
