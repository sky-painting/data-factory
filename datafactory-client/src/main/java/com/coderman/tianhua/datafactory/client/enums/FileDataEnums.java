package com.coderman.tianhua.datafactory.client.enums;

import java.io.File;

/**
 * Description:
 * date: 2021/1/14
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum  FileDataEnums {
    /**
     *
     */
    FIRST_NAME("firstname.txt","中国姓氏","parseFirstNameServiceImpl"),
    /**
     *
     */
    LAST_NAME("lastname.txt","中国名称","parseLastNameServiceImpl"),

    ;

    FileDataEnums(String fileName,String desc,String parseBeanName){
        this.fileName = fileName;
        this.desc = desc;
        this.parseBeanName = parseBeanName;
    }
    private String fileName;
    private String desc;
    /**
     * ParseService 实现类-spring bean name
     */
    private String parseBeanName;


    public String getFileName() {
        return fileName;
    }

    public String getDesc() {
        return desc;
    }

    public String getParseBeanName() {
        return parseBeanName;
    }

    public static String getBeanName(String fileName){
        for (FileDataEnums fileDataEnums : FileDataEnums.values()){
            if(fileDataEnums.getFileName().equals(fileName)){
                return fileDataEnums.getParseBeanName();
            }
        }
        return null;
    }
}
