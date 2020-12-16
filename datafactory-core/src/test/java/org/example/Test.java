package org.example;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * description: Test <br>
 * date: 2020/12/16 23:31 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public class Test {
    public static void main(String[] args) {
        String content = "赵钱孙李,周吴郑王\n" +
                "冯陈褚卫,蒋沈韩杨\n" +
                "朱秦尤许,何吕施张\n" +
                "孔曹严华,金魏陶姜\n" +
                "戚谢邹喻,柏水窦章\n" +
                "云苏潘葛,奚范彭郎\n" +
                "鲁韦昌马,苗凤花方\n" +
                "俞任袁柳,酆鲍史唐\n" +
                "费廉岑薛,雷贺倪汤\n" +
                "滕殷罗毕,郝邬安常";
        String [] array = content.split("\n");
        List<String> list = new ArrayList<>();
        for (String value : array){
            String [] group = value.split(",");
            for (String gStr : group){
                char [] c =  gStr.toCharArray();
                for (char cx : c){
                    list.add(cx+"");
                }
            }
        }
        System.out.println(JSON.toJSONString(list));
    }
}
