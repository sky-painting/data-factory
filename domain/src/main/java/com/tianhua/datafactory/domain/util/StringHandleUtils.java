package com.tianhua.datafactory.domain.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * date: 2021/10/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class StringHandleUtils {

    private static Set<String> basicTypeSet = Sets.newHashSet("Integer","int"
            ,"long","Long"
            ,"String",
            "Float","float",
            "Boolean","boolean",
            "Short","short"
            ,"Double","double","[]","Object");

    /**
     * 严格基本数据类型
     */
    private static Set<String> strictBasicTypeSet = Sets.newHashSet("int"
            ,"long"
            ,"float"
            ,"boolean"
            ,"short"
            ,"double");


    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
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

    /**
     * 通过变量类型获取变量对应的变量名
     * @param paramType
     * @return
     */
    public static String getParamVar(String paramType){
        if(StringUtils.isEmpty(paramType.trim())){
            return "null";
        }
        if(paramType.contains("List<")){
            String paramModel = paramType.trim().replace("List<","").replace(">","");
            return paramModel.trim().substring(0,1).toLowerCase()+paramModel.substring(1)+"List";
        }
        if(paramType.contains("Set<")){
            String paramModel = paramType.trim().replace("List<","").replace(">","");
            return  paramModel.trim().substring(0,1).toLowerCase()+paramModel.substring(1)+"Set";
        }

        return paramType.trim().substring(0,1).toLowerCase()+paramType.substring(1);
    }

    /**
     * 判断参数是不是基本类型
     * @param paramType
     * @return
     */
    public static boolean isBasicType(String paramType){
        AtomicBoolean basicType = new AtomicBoolean(false);
        basicTypeSet.forEach(str->{
            if(paramType.contains(str)){
                basicType.set(true);
            }
        });
        return basicType.get();
    }

    /**
     * 判断参数是不是严格的基本类型
     * @param paramType
     * @return
     */
    public static boolean isStrictBasicType(String paramType){
        AtomicBoolean basicType = new AtomicBoolean(false);
        strictBasicTypeSet.forEach(str->{
            if(paramType.contains(str)){
                basicType.set(true);
            }
        });
        return basicType.get();
    }


    /**
     * 将返回参数包装类型去掉
     * Result
     * ResultDto
     * ResultDataDto
     * SdkResponse
     * Response
     * ResultDto<xxx>
     * ResultDto<List<xxxx>>
     * SdkResponse<xxx>
     * @param paramType
     * @return
     */
    public static String getParamTypeNoWrapper(String paramType){
        boolean wrapper = paramType.contains("Result") || paramType.equals("Response");
        //如果是包装类型，但是却没有范型声明则返回null
        if(wrapper){
            if(!paramType.contains("<")){
                return null;
            }
            String result = paramType.substring(paramType.indexOf("<")+1);
            return result.substring(0,result.length() - 1);
        }
        return paramType;
    }
}
