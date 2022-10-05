package com.tianhua.datafactory.core.service.impl;

import com.tianhua.datafactory.core.service.ERPlantUMLParseService;
import com.tianhua.datafactory.domain.GlobalConstant;
import com.tianhua.datafactory.domain.bo.model.ColumnBO;
import com.tianhua.datafactory.domain.bo.model.TableBO;
import com.tianhua.datafactory.domain.enums.ColumnTypeEnums;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * date: 2021/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ERPlantUMLParseServiceImpl implements ERPlantUMLParseService {

    /**
     * 解析plantUML文件内容
     *
     * @return
     */
    public List<TableBO> getPlantUmlContextBean(List<String> contentList) {
        if(CollectionUtils.isEmpty(contentList)){
            return null;
        }

        List<String> elementList = new ArrayList<>();
        List<TableBO> entityBeanList = new ArrayList<>();
        //对entity进行解析
        for (String str : contentList) {
            if (StringUtils.isEmpty(str) || str.contains("@startuml") || str.contains("package")) {
                continue;
            }

            if (str.trim().contains("{")) {
                elementList.add(str.trim());
                continue;
            }

            if (str.trim().contains("}")) {
                elementList.add(str.trim());
                parseClassElement(elementList, entityBeanList);
                elementList.clear();
                continue;
            }
            if (str.trim().contains(":") || (str.trim().contains("key") && str.trim().contains("extend"))) {
                elementList.add(str.trim());
            }
        }

        return entityBeanList;
    }


    /**
     * 解析文件内容整体路由
     *
     * @param elementList
     * @param entityBeanList
     */
    private void parseClassElement(List<String> elementList, List<TableBO> entityBeanList) {
        TableBO tableBO = buildClassBean(elementList);
        entityBeanList.add(tableBO);
    }


    /**
     * 解析class类型数据
     *
     * @param elementList
     * @return
     */
    private TableBO buildClassBean(List<String> elementList) {
        TableBO tableBO = new TableBO();

        String[] array = elementList.get(0).trim().replace("{", "")
                .replace("entity", "")
                .replace("as ", "")
                .replace("\"", "").trim().split(" ");

        List<ColumnBO> columnBeanList = getColumnBeanList(elementList.subList(1, elementList.size()));
        //List<ColumnBO> indexBeanList = getIndexBeanList(elementList.subList(1, elementList.size()));
        tableBO.setColumnList(columnBeanList);
        //entityBean.setIndexBeanList(indexBeanList);

        tableBO.setTableName(array[0]);
        tableBO.setTableComment(array[1]);

        return tableBO;
    }


    /**
     * 获取表字段信息
     *
     * @param elementList
     * @return
     */
    private List<ColumnBO> getColumnBeanList(List<String> elementList) {
        List<ColumnBO> columnBOList = new ArrayList<>();

        for (String fieldStr : elementList) {
            if (fieldStr.contains("key") && fieldStr.contains("extend")) {
                break;
            }

            if (!fieldStr.trim().contains(":")) {
                continue;
            }

            String[] fieldArr = fieldStr.trim().split(":");
            ColumnBO columnBO = new ColumnBO();
            String[] tagArr = fieldArr[1].split("/");

            columnBO.setColumnComment(tagArr[0]);
            columnBO.setColumnName(fieldArr[0]);
            if (tagArr.length == 2) {
                if(tagArr[1].contains("(")){
                    columnBO.setColumnType(tagArr[1].substring(0,tagArr[1].indexOf("(")));
                }else {
                    columnBO.setColumnType(tagArr[1]);
                }
            }
            if (tagArr.length == 3) {
                columnBO.setDefaultValue(tagArr[1]);
                if(tagArr[2].contains("(")){
                    columnBO.setColumnType(tagArr[2].substring(0,tagArr[2].indexOf("(")));
                }else {
                    columnBO.setColumnType(tagArr[2]);
                }
            }
            if (StringUtils.isEmpty(columnBO.getDefaultValue())) {
                if (ColumnTypeEnums.isInt(columnBO.getColumnType())) {
                    columnBO.setDefaultValue("0");
                }
                if (ColumnTypeEnums.isVarchar(columnBO.getColumnType())) {
                    columnBO.setDefaultValue("''");
                }

                if (ColumnTypeEnums.isJson(columnBO.getColumnType())) {
                    columnBO.setDefaultValue("{}");
                }


                if (ColumnTypeEnums.isDate(columnBO.getColumnType())) {
                    columnBO.setDefaultValue("'2000-01-01 00:00:00'");
                }
            }

            if(columnBO.getColumnType().contains("(")){
               String columnLength = columnBO.getColumnType().substring(columnBO.getColumnType().indexOf("(")+1, columnBO.getColumnType().indexOf(")"));
                columnBO.setColumnLength(Integer.parseInt(columnLength));
            }else {
                columnBO.setColumnLength(0);
            }
           // entityFieldBean.setNullable(false);
            columnBOList.add(columnBO);
        }
        return columnBOList;
    }

    /**
     * 获取索引信息
     *
     * @param elementList
     * @return
     */
    private List<ColumnBO> getIndexBeanList(List<String> elementList) {
        List<ColumnBO> indexBeanList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < elementList.size(); i++) {

            if (elementList.get(i).contains("key") && elementList.get(i).contains("extend")) {
                index = i + 1;
                break;
            }
        }


        for (String fieldStr : elementList.subList(index, elementList.size())) {
            ColumnBO indexFieldBean = new ColumnBO();
            if (!fieldStr.contains(":")) {
                continue;
            }
            String[] indexArr = fieldStr.split(":");
            if (indexArr[0].toLowerCase().equals(GlobalConstant.PKEY)) {
                //indexFieldBean.setPkey(true);
                indexFieldBean.setColumnName(indexArr[1]);
                indexBeanList.add(indexFieldBean);
            }
            if (indexArr[0].toLowerCase().equals(GlobalConstant.UKEY)) {
                //indexFieldBean.setUKey(true);
                indexFieldBean.setColumnName(indexArr[1]);
                indexBeanList.add(indexFieldBean);
            }
            if (indexArr[0].toLowerCase().equals(GlobalConstant.KEY)) {
                indexFieldBean.setColumnName(indexArr[1]);
                indexBeanList.add(indexFieldBean);
            }

        }
        return indexBeanList;
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
