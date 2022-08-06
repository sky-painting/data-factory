package com.tianhua.datafactory.controller.admin;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.vo.datafactory.DataFactoryRequestVo;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.vo.datasource.DataSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * description: DataFactoryController <br>
 * date: 2020/12/2 23:43 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RestController
@Slf4j
public class DataFactoryController extends BaseController {


    /**
     * @param dataSourceVo
     * @return ResultDataDto 构建结果
     * @Description:根据数据源构建数据
     * @version v1.0
     */
    @RequestMapping(value = "/datafactory/generate", method = RequestMethod.GET)
    public ResultDataDto generate(@RequestBody DataSourceVO dataSourceVo) {
        return null;
    }

    /**
     * @param dataFactoryRequestVo
     * @return ResultDataDto 构建结果
     * @Description:根据数据源构建数据 适用于单表，或者单模块构建
     * @version v1.0
     */
    @RequestMapping(value = "/datafactory/generate/simple", method = RequestMethod.POST)
    public ResultDataDto generateSimple(@RequestBody DataFactoryRequestVo dataFactoryRequestVo) {
        logger.info("dataFactoryRequestVo = {}", JSON.toJSONString(dataFactoryRequestVo));
        ResultDataDto resultDataDto = new ResultDataDto();
        return resultDataDto;
    }


}
