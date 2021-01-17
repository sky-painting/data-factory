package com.coderman.tianhua.datafactory.client.service;

import java.util.List;

/**
 * Description:
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
}
