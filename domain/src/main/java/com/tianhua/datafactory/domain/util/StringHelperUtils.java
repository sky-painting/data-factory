package com.tianhua.datafactory.domain.util;

import java.util.regex.Pattern;

/**
 * Description:
 * date: 2021/10/21
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class StringHelperUtils {

    private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");


    /**
     * 根据表名获取对应类名
     * @param tableName
     * @return
     */
    public static String getClassDOName(String tableName){
        String humpTableName = getHumpTableName(tableName);
        return getHumpClassName(humpTableName);
    }

    /**
     * 获取表名对应的类名
     * eg: user_info->UserInfo
     * staff_education_info->StaffEducationInfo
     *
     * @param humpTableName
     * @return
     */
    public static String getHumpClassName(String humpTableName){
        String resultName = humpTableName.substring(0,1).toUpperCase().concat(humpTableName.substring(1));
        return resultName;
    }

    /**
     * 获取表名对应的变量名
     * eg: user_info->userInfo
     * staff_education_info->staffEducationInfo
     *
     * @param tableName
     * @return
     */
    private static String getHumpTableName(String tableName){
        String resultName = "";

        if(!tableName.contains("_")){
            resultName = tableName;
        }else {
            String[] tableNameArr = tableName.split("_");
            int length = tableNameArr.length;
            StringBuilder builder = new StringBuilder();

            if(isNum(tableNameArr[length - 1])){
                if(length == 2){
                    resultName = tableNameArr[0];
                }else {
                    builder.append(tableNameArr[0]);
                    for (int i = 1;i < length - 1;i++){
                        String tag = tableNameArr[i].substring(0,1).toUpperCase().concat(tableNameArr[i].substring(1));
                        builder.append(tag);
                    }
                    resultName = builder.toString();
                }
            }else {
                builder.append(tableNameArr[0]);
                for (int i = 1;i < length;i++){
                    String tag = tableNameArr[i].substring(0,1).toUpperCase().concat(tableNameArr[i].substring(1));
                    builder.append(tag);
                }
                resultName = builder.toString();
            }

        }
        return resultName;
    }


    private static boolean isNum(String str){
        return pattern.matcher(str).matches();
    }

}
