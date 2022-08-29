package com.tianhua.datafactory.domain.ability;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tianhua.datafactory.domain.GlobalConstant;
import com.tianhua.datafactory.domain.bo.EnumBean;
import com.tianhua.datafactory.domain.bo.MethodBean;
import com.tianhua.datafactory.domain.bo.PlantUMLApiContextBean;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import com.tianhua.datafactory.domain.bo.project.ApiBO;
import com.tianhua.datafactory.domain.enums.ClassRelationEnum;
import com.tianhua.datafactory.domain.enums.VisibilityEnum;
import com.tianhua.datafactory.domain.util.StringHandleUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2022/4/8
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class ReadApiPlantUMLDocService {

    @Autowired
    private ReadPlantUMLDocService readPlantUMLDocService;

    //@Autowired
    //private RefreshService refreshService;

    private RefreshService refreshService = new RefreshService();

    /**
     * 适用于web上传方式
     * @param filePath
     * @return
     */
    public PlantUMLApiContextBean readDoc(String filePath){
        try {
            List<String> contentList = FileUtils.readLines(new File(filePath),"utf-8");
            PlantUMLApiContextBean plantUMLApiContextBean = exeParse(contentList);
            refreshService.refresh(plantUMLApiContextBean);
            return plantUMLApiContextBean;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private PlantUMLApiContextBean exeParse(List<String> contentList){
        List<String> elementList = new ArrayList<>();
        PlantUMLApiContextBean plantUmlContextBean = new PlantUMLApiContextBean();

        String currentPackage= "";
        //对class,enum,interface进行解析
        for(String str : contentList){
            String content = str.trim();
            if(StringUtils.isEmpty(content) || content.contains("@startuml") || content.startsWith(GlobalConstant.PLANT_DOC_IGNORE)){
                continue;
            }

            if(content.contains("package")){
                currentPackage = content.replace("package","").replace("{","").trim();
                continue;
            }

            if(content.contains("{")){
                elementList.add(content);
                continue;
            }
            if(content.contains("}")){
                elementList.add(content);
                parseClassElement(elementList,plantUmlContextBean,currentPackage);
                elementList.clear();
                continue;
            }
            elementList.add(content);
        }

        //对类与类的关系进行解析
        Map<String, List<String>> relationListMap = new HashMap<>();
        for(String str : contentList){
            if(StringUtils.isEmpty(str) || str.contains("@startuml")
                    || str.contains("package") || str.trim().contains("{") || str.trim().contains("}")){
                continue;
            }

            Map<String, String> relationMap = ClassRelationEnum.parseRelation(str);
            if(relationMap == null || relationMap.size()<2){
                continue;
            }
            List<String> relationList =  relationListMap.get(relationMap.get("class"));
            if(CollectionUtils.isEmpty(relationList)){
                relationList = new ArrayList<>();
            }
            relationList.add(relationMap.get("relation"));
            relationListMap.put(relationMap.get("class").trim(),relationList);
        }

        return plantUmlContextBean;
    }


    /**
     * 解析文件内容整体路由
     * @param elementList
     * @param plantUMLApiContextBean
     */
    private void parseClassElement(List<String> elementList, PlantUMLApiContextBean plantUMLApiContextBean, String currentPackage){
        String className = getClassName(elementList.get(0));
        if(className.toLowerCase().endsWith(GlobalConstant.API)){
            List<ApiBO> apiBeanList = buildApiBean(elementList);
            plantUMLApiContextBean.addBatchApiBean(apiBeanList);
        }

        if(className.toLowerCase().endsWith(GlobalConstant.DTO) || className.toLowerCase().endsWith(GlobalConstant.VO)){
            ParamModelBO paramClassbean = buildClassBean(elementList);
            plantUMLApiContextBean.addParamClassBean(paramClassbean);
        }
        else if(className.toLowerCase().endsWith("enum")){
            EnumBean enumBean = builEnumBean(elementList);
            enumBean.buildPlantUMLPackage(currentPackage);
            plantUMLApiContextBean.addEnumBean(enumBean);
        }

    }

    /**
     * 解析enum类型数据
     * @param elementList
     * @return
     */
    private EnumBean builEnumBean(List<String> elementList ){

        String[] array = elementList.get(0).trim().replace("{","").trim().split("\"");

        String classMetaInfoArr = array[1];
        List<FieldBO> fieldBeanList = getFieldBeanList(elementList.subList(1,elementList.size()));
        EnumBean enumBean = new EnumBean();
        enumBean.setFieldBeanList(fieldBeanList);
        enumBean.setClassName(classMetaInfoArr.split("-")[1].replace("\"",""));
        enumBean.setClassDesc(classMetaInfoArr.split("-")[0].replace("\"",""));
        if(StringHandleUtils.isContainChinese(enumBean.getClassName())){
            String className = enumBean.getClassDesc();
            enumBean.setClassDesc(enumBean.getClassName());
            enumBean.setClassName(className);
        }
        dealEnumMethodBeanList(elementList,enumBean);
        return enumBean;
    }
    /**
     * 获取类的方法列表
     * @param elementList
     * @return
     */
    private void dealEnumMethodBeanList(List<String> elementList, EnumBean enumBean){
        List<MethodBean> methodBeanList = new ArrayList<>();
        List<Map<String,String>> enumValueList  = new ArrayList<>();
        for (String fieldStr : elementList){
            if(!fieldStr.contains("()") && !fieldStr.contains("(") && !fieldStr.contains(")")){
                continue;
            }

            //处理枚举值
            String valueCode = fieldStr.substring(0,fieldStr.indexOf("("));
            if(!StringUtils.isEmpty(valueCode) && !valueCode.contains(" ")){
                enumValueList.add(buildEnumValueMap(enumBean,fieldStr));
                continue;
            }

            MethodBean methodBean =new MethodBean();

            if(fieldStr.contains(":")){
                String[] fieldArr = fieldStr.trim().split(":");
                methodBean.setVisibility(VisibilityEnum.getVisibilityStr(fieldArr[0]));
                methodBean.setDesc(fieldArr[0]);
                methodBean.setReturnClass(fieldArr[1].trim().split(" ")[0]);
                methodBean.setMethodName(fieldArr[1].trim().split(" ")[1]);
            }else {
                String[] fieldArr = fieldStr.trim().split(" ");
                //没有注释--->中文校验
                if(fieldArr.length ==2){
                    methodBean.setVisibility(VisibilityEnum.getVisibilityStr(fieldStr.trim()));
                    methodBean.setReturnClass(fieldStr.trim().split(" ")[0]);
                    methodBean.setMethodName(fieldStr.trim().split(" ")[1]);
                }else{
                    methodBean.setDesc(fieldArr[0]);
                    methodBean.setVisibility(VisibilityEnum.getVisibilityStr(fieldStr.trim()));
                    methodBean.setReturnClass(fieldArr[1]);
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2;i<fieldArr.length;i++){
                        builder.append(" "+fieldArr[i]);
                    }
                    methodBean.setMethodName(builder.toString());
                }
            }
            if(!methodBean.getReturnClass().contains("void")){
                methodBean.setReturnBody("return null;");
            }

            methodBeanList.add(methodBean);
        }
        enumBean.setEnumValueList(enumValueList);
        enumBean.setMethodBeanList(methodBeanList);
    }


    /**
     * 解析class类型数据
     * @param elementList
     * @return
     */
    private ParamModelBO buildClassBean(List<String> elementList ){

        String[] array = elementList.get(0).trim().replace("{","").trim().split("\"");

        String classMetaInfoArr = array[1];
        List<FieldBO> fieldBeanList = getFieldBeanList(elementList.subList(1,elementList.size()));
        ParamModelBO classBean = new ParamModelBO();
        classBean.setFieldBeanList(fieldBeanList);

        classBean.setParamClassName(classMetaInfoArr.split("-")[1].replace("\"",""));
        classBean.setParamClassDesc(classMetaInfoArr.split("-")[0].replace("\"",""));

        return classBean;
    }


    private String getClassName(String ele){
        String [] classArr = ele.trim().replace("{","").split(" ");
        return  classArr[classArr.length - 1];
    }

    /**
     * 解析class类型数据
     * @param elementList
     * @return
     */
    private List<ApiBO> buildApiBean(List<String> elementList ){

        List<ApiBO> apiBeanList = new ArrayList<>();
        String moduleCode = "";
        String moduleName = "";
        for (String fieldStr : elementList.subList(1,elementList.size())){

            if(fieldStr.trim().contains("extend info")){
                break;
            }
            if(!fieldStr.trim().contains(":")){
                continue;
            }

            if(!fieldStr.contains("(") && StringUtils.isEmpty(moduleName)){
                String [] fieldArr = fieldStr.trim().split("-");
                String moduleInfo = fieldArr[1];
                moduleName = moduleInfo.split(":")[0].trim();
                moduleCode = moduleInfo.split(":")[1].trim();
                continue;
            }
            String[] fieldArr = fieldStr.trim().split(":");
            ApiBO apiBean = new ApiBO();
            apiBean.setApiDoc(fieldArr[0]);
            String [] methodArr = fieldArr[1].trim().split("\\.");
            apiBean.setMethodType(methodArr[1]);
            String [] methodReturnArr = methodArr[0].split("\\(");
            String returnParam = methodReturnArr[0].split(" ")[0];
            ParamModelBO returnParamBean = new ParamModelBO();
            returnParamBean.setParamClassName(returnParam);
            apiBean.setReturnParamModel(returnParamBean);

            apiBean.setApiUrl(methodReturnArr[0].split(" ")[1]);
            String param = methodReturnArr[1].replace(")","");

            apiBean.setParamList(buildParamBean(param));
            apiBean.setModuleCode(moduleCode);
            apiBean.setModuleName(moduleName);
            apiBeanList.add(apiBean);
        }

        return apiBeanList;
    }


    /**
     * 构建枚举
     * @param enumBean
     * @param valueStr
     * @return
     */
    private Map<String,String> buildEnumValueMap(EnumBean enumBean,String valueStr){
        List<FieldBO> fieldBOList = enumBean.getFieldBeanList();

        String [] valueArr = valueStr.split("\\(");
        String valueSegment = valueArr[1];
        String valueSeg = valueSegment.substring(0,valueSegment.indexOf(")"));
        Map<String,String> valueMap = new HashMap<>();

        if(!valueSeg.contains(",")){
            if(valueSeg.contains("\"")){
                valueSeg = valueSeg.replace("\"","");
            }
            valueMap.put(fieldBOList.get(0).getFieldName(),valueSeg);
        }
        String [] valueSegArr = valueSeg.split(",");

        for (int i = 0;i < fieldBOList.size();i ++){
            valueSeg = valueSegArr[i];
            if(valueSeg.contains("\"")){
                valueSeg = valueSeg.replace("\"","");
            }
            valueMap.put(fieldBOList.get(i).getFieldName(),valueSeg);
        }
        return valueMap;
    }


    /**
     * 构建参数模型
     * @param param
     * @return
     */
    private List<ParamModelBO> buildParamBean(String param){
        if(StringUtils.isEmpty(param.trim())){
            return Lists.newArrayList();
        }
        List<ParamModelBO> paramBeanList = new ArrayList<>();
        if(!param.contains(",")){
            if(param.contains(" ")){
                String [] paramArr = param.split(" ");
                ParamModelBO paramBean = ParamModelBO.getInstance(paramArr[0], paramArr[1]);
                paramBeanList.add(paramBean);
            }
            return paramBeanList;
        }

        String [] paramArr = param.split(",");
        for (String paramStr : paramArr){
            if(paramStr.contains(" ")){
                String [] paramStrArr = paramStr.split(" ");
                ParamModelBO paramBean = ParamModelBO.getInstance(paramStrArr[0],paramStrArr[1]);
                paramBeanList.add(paramBean);
            }
        }
        return paramBeanList;
    }


    /**
     * 获取类的属性列表
     * @param elementList
     * @return
     */
    private List<FieldBO> getFieldBeanList(List<String> elementList){
        List<FieldBO> fieldBeanList = new ArrayList<>();
        int extendIndex = 0;

        for (int i = 0 ;i< elementList.size();i ++){
            String fieldStr = elementList.get(i);
            if(isExtendLine(fieldStr)){
                extendIndex = i;
            }
            if(fieldStr.contains("(") || fieldStr.contains(")")){
                continue;
            }
            if(!fieldStr.trim().contains(":")){
                continue;
            }

            String[] fieldArr = fieldStr.trim().split(":");
            FieldBO fieldBean = new FieldBO();
            if(i > extendIndex && extendIndex > 0){
                fieldBean.setExtendFieldTag(true);
            }
            fieldBean.buildDesc(fieldArr[0]);
            if(fieldArr[1].trim().contains(" ")){
                String [] fields = fieldArr[1].trim().split(" ");
                fieldBean.setFieldName(fields[1]);
                fieldBean.setFieldType(fields[0]);
            }else {
                fieldBean.setFieldName(fieldArr[1]);
            }
            fieldBeanList.add(fieldBean);
        }
        return fieldBeanList;
    }

    /**
     * 判断是否是扩展标示
     * @param content
     * @return
     */
    private boolean isExtendLine(String content){
        return content.contains("..") && content.contains("extend") && content.contains("info");
    }


    public static void main(String[] args) {
        ReadApiPlantUMLDocService readApiPlantUMLDocService = new ReadApiPlantUMLDocService();
        String path = "/Users/dasouche/Documents/api-model.puml";
        PlantUMLApiContextBean plantUMLApiContextBean = readApiPlantUMLDocService.readDoc(path);
        System.out.println(JSON.toJSONString(plantUMLApiContextBean));
    }
}
