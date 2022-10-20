package com.coderman.tianhua.datafactory.api.test;

/**
 * description: Main <br>
 * date: 2020/12/10 23:27 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public class Main {
    public static void main(String[] args) {
        String code = "com.lightsnail.snail.room.room_source_type";
        String groupId = code.substring(code.lastIndexOf(".")+1);
        String dataId = code.substring(0,code.lastIndexOf("."));

        System.out.println(dataId);
        System.out.println(groupId);

    }
}
