package com.coderman.tianhua.datafactory.client.filedata;

import java.util.List;

/**
 * Description:
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface ParseService {
    /**
     * 通过文件名解析文件内容并返回文件数据
     * @return
     */
    List<String> parseFileData();

}
