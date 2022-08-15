package com.tianhua.datafactory.core.service.liteflow;

import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;

/**
 * Description
 * 根据创建条数判断是否进行并行或者串行的数据构建
 * date: 2022/8/13
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@LiteflowComponent(id = "switchProcessingCmp", name = "组装数据")
public class SwitchProcessingCmp extends NodeSwitchComponent {


    @Override
    public String processSwitch() throws Exception {


        DataBuildRequestBO dataBuildRequestBO = this.getRequestData();

        if(dataBuildRequestBO.getBuildCount() > 10000){
            return "parallelProcessingCmp";
        }

        return "serialProcessingCmp";
    }
}
