package com.tianhua.datafactory.core.service.liteflow;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * Description
 * 根据创建条数判断是否进行并行或者串行的数据构建
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@LiteflowComponent(id = "switchProcessingCmp", name = "数据构建模式选择器")
@Slf4j
public class SwitchProcessingCmp extends NodeSwitchComponent {


    @Override
    public String processSwitch() throws Exception {


        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();

        if(dataBuildRequestBO.getBuildCount() == null){
            log.error("当前数据源没有配置构建数量,默认为1,dataBuildRequestBO = {}", JSON.toJSONString(dataBuildRequestBO));
            dataBuildRequestBO.setBuildCount(1);
            return "serialProcessingCmp";
        }

        if(dataBuildRequestBO.getBuildCount() > 10000){
            return "parallelProcessingCmp";
        }

        return "serialProcessingCmp";
    }
}
