package com.tianhua.datafactory.domain.bo;

import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * description: DataFactoryVo <br>
 * date: 2020/12/2 23:48 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
@Data
@ToString
@Deprecated
public class DataBuildRequestBean {
    /**
     * 项目编码
     */
    private String projectCode;


    /**
     * 创建多少条数据 max 100w
     */
    private int generateCount;



    /**
     * 模块变量
     */
    private List<DataBuildRequestFieldBO> dataFactoryRequestFieldBeanList;

    /**
     * 是否持久化数据构建请求以复用
     */
    private int persistRequest;

}
