package com.coderman.tianhua.datafactory.api.controller;

import com.coderman.tianhua.datafactory.core.vo.DataSourceVO;
import com.coderman.utils.response.ResultDataDto;
import com.coderman.utils.response.ResultDto;
import lombok.extern.slf4j.Slf4j;
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
     * @Description:根据数据源构建数据
     * @version v1.0
     * @param dataSourceVo
     * @return ResultDataDto 构建结果
     */
    @RequestMapping(value = "/data/factory/generate",method = RequestMethod.GET)
    public ResultDataDto save(@RequestBody DataSourceVO dataSourceVo){
       return null;
    }

}
