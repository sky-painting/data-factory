package com.tianhua.datafactory.client.filedata;

import com.tianhua.datafactory.client.context.FieldIndex;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description
 * date: 2022/9/4
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class AbstractParseService {

    private static SecureRandom secureRandom = new SecureRandom();

    private static SimpleDateFormat formatWithLine = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat formatWithSplit = new SimpleDateFormat("yyyy/MM/dd");

    private static SimpleDateFormat formatDataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




    /**
     * 从一个集合中随机获取一部分子集
     * 尽量高效
     *
     * @param contentList  集合元素
     * @param count   取出子集的数量
     * @return
     */
    public List<String> getRandomList(List<String> contentList, Integer count ){
        if(count > contentList.size()){
            return contentList;
        }

        List<String> resultList = new ArrayList<>();

        Set<Integer> integerSet = new HashSet<>();

        Set<Integer> totalSet = new HashSet<>();

        for (int i = 0 ;i < contentList.size();i++) {
            totalSet.add(i);
        }


        while (resultList.size() < count){
            Integer index = secureRandom.nextInt(contentList.size());

            double currentRate = (double) resultList.size() / (double) count;

            if(currentRate > 0.75f){
                totalSet.removeAll(integerSet);
                totalSet.stream().forEach(integer -> {
                    if(resultList.size() < count){
                        resultList.add(contentList.get(integer));
                    }
                });
                break;
            }

            while (integerSet.contains(index) && index >= 0){
               index --;
            }
            if(index >= 0 ){
                resultList.add(contentList.get(index));
                integerSet.add(index);
                continue;
            }


            while (integerSet.contains(index) && index < contentList.size()){
                index ++;
            }
            if(index < contentList.size() && index >= 0 ){
                resultList.add(contentList.get(index));
                integerSet.add(index);
            }
        }

        return resultList;
    }


    /**
     * 收集单个元素
     * @param fieldIndex
     * @param value
     * @param rowMap
     */
    public void buildRowMap(FieldIndex fieldIndex, String value, Map<String, Object> rowMap) throws ParseException {

        if(fieldIndex.isLong()){
            rowMap.put(fieldIndex.getFieldName(), Long.valueOf(value));
        }
        else if(fieldIndex.isInteger()){
            rowMap.put(fieldIndex.getFieldName(), Integer.valueOf(value));
        }

        else if(fieldIndex.isShort()){
            rowMap.put(fieldIndex.getFieldName(), Short.valueOf(value));
        }

        else if(fieldIndex.isString()){
            rowMap.put(fieldIndex.getFieldName(), value);
        }

        else if(fieldIndex.isDate()){
            Date date = new Date();
            if(value.contains("-")){
                date = formatWithLine.parse(value);
            }
            if(value.contains("/")){
                date = formatWithSplit.parse(value);
            }
            rowMap.put(fieldIndex.getFieldName(), date);
        }
        else if(fieldIndex.isDateTime()){
            rowMap.put(fieldIndex.getFieldName(), formatDataTime.parse(value));
        }
    }




    public static void main(String[] args) {
        AbstractParseService abstractParseService = new AbstractParseService();



        List<String> list = new ArrayList<>();
        int length = 1000000;
        for (int i = 0;i < length;i ++){
            list.add(i+"");
        }

        long startTime = System.currentTimeMillis();
        List<String> resultList = abstractParseService.getRandomList(list,100000);
        long endTime = System.currentTimeMillis();

        Set<String> set = new HashSet<>(resultList);
        System.out.println("set.size = "+set.size()+",listsize = "+resultList.size()+",endTime - startTime = "+(endTime - startTime)+"ms");

    }
}
