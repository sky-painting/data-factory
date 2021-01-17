package com.coderman.tianhua.datafactory.core.functionfactory;

import com.coderman.tianhua.datafactory.client.function.Function;

/**
 * description: FunctionFactory <br>
 * date: 2020/12/28 23:38 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 * function工厂
 */
public interface FunctionFactory {

    /**
     * 创建一个函数 基于元数据文件
     * @param dataSourceCode 内置字段数据源code
     * @param dependencyFileDataList 依赖的文件列表
     * @return
     */
   // public Function createFunction(String dataSourceCode, List<String> dependencyFileDataList);

    /**
     * 创建一个函数
     * @param dataSourceCode 内置字段数据源code
     * @return
     */
    public Function createFunction(String dataSourceCode);

}
