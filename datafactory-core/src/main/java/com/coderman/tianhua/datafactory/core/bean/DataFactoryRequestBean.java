package com.coderman.tianhua.datafactory.core.bean;

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
public class DataFactoryRequestBean {
    /**
     * 项目名称--中文
     */
    private String projectName;

    /**
     * 服务名称--英文
     */
    private String serviceName;

    /**
     * 模块名称--英文
     */
    private String moduleName;

    /**
     *  模块名称--中文
     */
    private String moduleDesc;

    /**
     * 创建多少条数据 max 100w
     */
    private int generateCount;

    /**
     * 属性的解析顺序
     */
    private List<String> dataFactoryRequestFieldOrderList;

    /**
     * 模块变量
     */
    private List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList;

    /**
     * 是否持久化数据构建请求以复用
     */
    private int persistRequest;

}
