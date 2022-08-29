package com.tianhua.datafactory.core.service;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.bo.DataBuildRequestBean;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;

import java.util.List;
import java.util.Map;

/**
 * description: DataFactoryservice
 * 数据工厂接口,生成仿真数据的入口
 * 有三种模式
 * 1.纯模型的生成
 * 2.根据api签名生成方法入参数据
 * 3.根据api签名生成方法出参数据
 * date: 2020/12/5 23:39 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public interface DataFactoryService {


   // ResultDataDto generateSimplex(List<DataFactoryRequestFieldBean> dataFactoryRequestFieldBeanList) throws Exception;

    /**
     * 根据数据工作台设置的数据属性生成规则构建一定数量的仿真数据
     *
     * 适用于自定义业务模型
     *
     *
     * @param dataBuildRequestBO
     * @return
     */
    ResultDataDto<List<Map<String, Object>>> generateData(DataBuildRequestBO dataBuildRequestBO) throws Exception;


    /**
     * 根据api签名构建api参数值随机列表
     * @param dataBuildRequestBO
     * @return
     * @throws Exception
     */
    ResultDataDto<List<Map<String, Object>>> generateDataApiReqParam(DataBuildRequestBO dataBuildRequestBO) throws Exception;

    /**
     * 根据api签名构建api返回值随机列表
     * @param dataBuildRequestBO
     * @return
     * @throws Exception
     */
    ResultDataDto<List<Map<String, Object>>> generateDataApiRespParam(DataBuildRequestBO dataBuildRequestBO) throws Exception;




    /**
     * 通过数据源编码获取单个数据源对应的随机数
     * 主要用于测试
     * @param dataBuildRequestFieldBO
     * @return
     */
    String buildData(DataBuildRequestFieldBO dataBuildRequestFieldBO) ;

}
