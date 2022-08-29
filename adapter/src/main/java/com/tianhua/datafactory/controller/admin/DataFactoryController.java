package com.tianhua.datafactory.controller.admin;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.core.service.DataFactoryService;
import com.tianhua.datafactory.vo.datafactory.DataBuildRequestVo;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.vo.datasource.DataSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * description: DataFactoryController <br>
 * date: 2020/12/2 23:43 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RestController
@Slf4j
public class DataFactoryController extends BaseController {

    @Autowired
    private DataFactoryService dataFactoryService;

    /**
     * @param dataFactoryRequestVo
     * @return ResultDataDto 构建结果
     * @Description:根据数据源构建数据
     * @version v1.0
     */
    @PostMapping(value = "/datafactory/generate")
    public ResultDataDto generate(@RequestBody DataBuildRequestVo dataFactoryRequestVo) {
        logger.info("dataFactoryRequestVo = {}", JSON.toJSONString(dataFactoryRequestVo));

        return ResultDataDto.success();
    }

    /**
     * @param dataFactoryRequestVo
     * @return ResultDataDto 构建结果
     * @Description:根据数据源构建数据 适用于单表，或者单模块构建
     * @version v1.0
     */
    @RequestMapping(value = "/datafactory/generate/simple", method = RequestMethod.POST)
    public ResultDataDto generateSimple(@RequestBody DataBuildRequestVo dataFactoryRequestVo) {
        logger.info("dataFactoryRequestVo = {}", JSON.toJSONString(dataFactoryRequestVo));

        ResultDataDto resultDataDto = new ResultDataDto();
        return resultDataDto;
    }

}
