package com.tianhua.datafactory.controller.admin;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.vo.StatusChangeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * 统一web状态管理控制层
 * date: 2022/9/6
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RestController
@Slf4j
@Deprecated
public class StatusManagerController  extends BaseController {

    /**
     * 统一状态修改入口
     * 这个接口当前这样传参是因为前端amis的bug导致ajax或者form无法完全把参数传入
     * @param moduleName
     * @return
     *
     */
    @Deprecated
    @RequestMapping(value = "statusmanager/update/{moduleName}",method = RequestMethod.POST)
    public ResultDataDto<Boolean> updateStatus(@PathVariable(value = "moduleName") String moduleName, @RequestBody StatusChangeVO statusChangeVO){
        logger.info("moduleName = {}",moduleName);
        logger.info("statusChangeVO = {}", JSON.toJSONString(statusChangeVO));
        return ResultDataDto.success();
    }

}
