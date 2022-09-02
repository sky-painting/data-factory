package com.tianhua.datafactory.client.service;

import com.tianhua.datafactory.client.context.FileDataSourceContext;

import java.io.IOException;
import java.util.List;

/**
 * Description:
 *
 * 支持从数据文件中读取数据源作为数据生成构建的来源
 * 实现方案
 * 1. 注册文件数据源和文件规则(文件存储,文件内部单行的解析规则，属性模型)
 * 2. 解析加载数据源到缓存中
 * 3. 根据数据源获取数据
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface FileDataService {
    /**
     * 从内置数据文件中获取随机数据列表
     * @param fileName
     * @return
     */
    List<String>  getFileDataList(String fileName);

    /**
     * 注册文件数据源
     * 从外部加载文件数据源
     *
     * 本地文件
     * web控制台上传的文件
     * @param fileDataSourceContext
     * @return
     */
    boolean registFileDataSource(FileDataSourceContext fileDataSourceContext) throws IOException;


    /**
     * 从文件中加载一定量的数据信息到缓存中
     * 按数据源编码#属性名的格式来加载
     * @param dataSourceCode 数据源编码
     * @param loadCount     加载数量
     * @return
     * @throws IOException
     */
    boolean  loadFileData(String dataSourceCode, Integer loadCount) throws IOException;


    /**
     * 根据数据源编码获取一个数据源对应的值信息
     * @param dataSourceCode
     * @return
     */
    Object getFileData(String dataSourceCode);

}
