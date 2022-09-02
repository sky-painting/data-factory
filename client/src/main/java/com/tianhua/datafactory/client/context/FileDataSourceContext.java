package com.tianhua.datafactory.client.context;

import java.util.List;

/**
 * Description
 * date: 2022/9/2
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class FileDataSourceContext {

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
     * 属性模型
     */
    private List<FieldIndex> fieldIndexBOList;


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSplitTag() {
        return splitTag;
    }

    public void setSplitTag(String splitTag) {
        this.splitTag = splitTag;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<FieldIndex> getFieldIndexBOList() {
        return fieldIndexBOList;
    }

    public void setFieldIndexBOList(List<FieldIndex> fieldIndexBOList) {
        this.fieldIndexBOList = fieldIndexBOList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(Integer loadCount) {
        this.loadCount = loadCount;
    }
}
