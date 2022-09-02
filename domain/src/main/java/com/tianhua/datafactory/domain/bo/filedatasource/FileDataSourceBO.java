package com.tianhua.datafactory.domain.bo.filedatasource;

import lombok.Data;

import java.util.List;

/**
 * Description
 * 文件资源模型
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class FileDataSourceBO {
    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 文件解析格式
     * 空格,逗号,分号,竖杠
     */
    private String splitTag;

    /**
     * txt,json,excel
     */
    private String fileType;

    /**
     * 属性模型
     */
    private List<FieldIndexBO> fieldIndexBOList;

}
