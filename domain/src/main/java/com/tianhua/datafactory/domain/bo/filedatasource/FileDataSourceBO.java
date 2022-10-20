package com.tianhua.datafactory.domain.bo.filedatasource;

import com.tianhua.datafactory.client.context.FieldIndex;
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
     * 方便本地化数据源管理
     * 用用户名作为标识来区分
     */
    private String userName;

    /**
     * 数据源编码
     * 全局唯一
     */
    private String dataSourceCode;

    /**
     * 数据源名称
     */
    private String dataSourceName;


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
     * 一次加载读取的数量
     * 例如1000w数据,随机取10w数据作为数据内容用来随机生成
     */
    private Integer readCount;

    /**
     * 总数量
     */
    private Integer totalCount;

    /**
     * 加载的数量
     */
    private Integer loadCount;

    /**
     * 要忽略多少条
     */
    private Integer skipCount;


    /**
     * 属性模型
     */
    private List<FieldIndexBO> fieldIndexBOList;

}
