package com.coderman.tianhua.datafactory.api.controller;

import com.alibaba.fastjson.JSON;
import com.coderman.tianhua.datafactory.api.vo.DataBuildRequestVo;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestBean;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldBean;
import com.coderman.tianhua.datafactory.core.bean.DataBuildRequestFieldRuleBean;
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

import java.util.ArrayList;
import java.util.List;


/**
 * description: DataFactoryController <br>
 * date: 2020/12/2 23:43 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@RestController
@Slf4j
public class DataBuildController extends BaseController {

    @Autowired
    private DataFactoryService dataFactoryService;

    @Autowired
    private CglibConvertService cglibConvertService;

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
    public ResultDataDto generateSimple(@RequestBody DataBuildRequestVo dataFactoryRequestVo) {
        logger.info("dataFactoryRequestVo = {}", JSON.toJSONString(dataFactoryRequestVo));
        ResultDataDto resultDataDto = new ResultDataDto();
        try {
            DataBuildRequestBean dataFactoryRequestBean = cglibConvertService.copyPropertity(DataBuildRequestBean.class, dataFactoryRequestVo);

            List<DataBuildRequestFieldBean> dataFactoryRequestFieldBeanList = new ArrayList<>();

            dataFactoryRequestVo.getDataFactoryRequestFieldVoList().stream().forEach(dataFactoryRequestFieldVo -> {
                try {
                    DataBuildRequestFieldBean dataFactoryRequestFieldBean = cglibConvertService.copyPropertity(DataBuildRequestFieldBean.class, dataFactoryRequestFieldVo);
                    if(dataFactoryRequestFieldVo.getDataFactoryRequestFieldRuleVo() != null){
                        DataBuildRequestFieldRuleBean dataFactoryRequestFieldRuleBean = cglibConvertService.copyPropertity(DataBuildRequestFieldRuleBean.class, dataFactoryRequestFieldVo.getDataFactoryRequestFieldRuleVo());
                        dataFactoryRequestFieldBean.setDataFactoryRequestFieldRuleBean(dataFactoryRequestFieldRuleBean);
                    }
                    dataFactoryRequestFieldBeanList.add(dataFactoryRequestFieldBean);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            dataFactoryRequestBean.setDataFactoryRequestFieldBeanList(dataFactoryRequestFieldBeanList);

            logger.info("dataFactoryRequestBean = {}", JSON.toJSONString(dataFactoryRequestVo));
            resultDataDto = dataFactoryService.generateSimple(dataFactoryRequestBean);
        } catch (Exception e) {
            resultDataDto.setInvokeErrorMsg("构建失败");
            logger.error("构建失败 ", e);
        }

        return resultDataDto;
    }


}
