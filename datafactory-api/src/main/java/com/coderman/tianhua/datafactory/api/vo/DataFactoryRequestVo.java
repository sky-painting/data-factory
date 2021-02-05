package com.coderman.tianhua.datafactory.api.vo;

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
public class DataFactoryRequestVo {
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
     * 模块变量
     */
    private List<DataFactoryRequestFieldVo> dataFactoryRequestFieldVoList;

    /**
     * 是否持久化数据构建请求以复用
     */
    private int persistRequest;


    /**
     * 增加远程动态插入接口参数
     */
    private String remoteServiceURL;


    /**
     * 增加表名
     */
    private String tableName;


    /**
     * 导出方式
     * 0:默认json返回 list模型
     * 1:转换为insert sql的形式
     * 2:转换为excel的形式写文件
     * 3:根据远程服务接口动态插入
     */
    private Integer exportType;
}
