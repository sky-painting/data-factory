package com.coderman.tianhua.datafactory.api.controller;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.vo.DataFactoryRequestFieldVo;
import com.coderman.tianhua.datafactory.api.vo.DataFactoryRequestVo;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataFactoryRequestFieldBean;
import com.coderman.tianhua.datafactory.core.service.DataFactoryService;
import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.bean.CglibConvertService;
import com.coderman.utils.response.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private CglibConvertService cglibConvertService;

    /**
     * @Description:根据数据源构建数据
     * @version v1.0
     * @param dataSourceVo
     * @return ResultDataDto 构建结果
     */
    @RequestMapping(value = "/data/factory/generate",method = RequestMethod.GET)
    public ResultDataDto generate(@RequestBody DataSourceVO dataSourceVo){
       return null;
    }

    /**
     * @Description:根据数据源构建数据
     * 适用于单表，或者单模块构建
     * @version v1.0
     * @param dataFactoryRequestVo
     * @return ResultDataDto 构建结果
     */
    @RequestMapping(value = "/data/factory/generate/simple",method = RequestMethod.GET)
    public ResultDataDto generateSimple(@RequestBody DataFactoryRequestVo dataFactoryRequestVo){
        logger.info("dataFactoryRequestFieldVoList = {}" , JSON.toJSONString(dataFactoryRequestVo));
        ResultDataDto resultDataDto = new ResultDataDto();
        try {
            DataFactoryRequestBean dataFactoryRequestBean = cglibConvertService.copyPropertity(DataFactoryRequestBean.class,dataFactoryRequestVo);
            logger.info("dataFactoryRequestBean = {}" , JSON.toJSONString(dataFactoryRequestVo));

            resultDataDto = dataFactoryService.generateSimple(dataFactoryRequestBean);
        } catch (Exception e) {
            resultDataDto.setInvokeErrorMsg("构建失败");
            logger.error("构建失败 ",e);
        }

        return resultDataDto;
    }



}
