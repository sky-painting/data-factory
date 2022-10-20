package org.example;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * description: Test2 <br>
 * date: 2020/12/16 23:33 <br>
 * author: coderman <br>
 * version: 1.0 <br>
 */
public class Test2 {
    public static void main(String[] args) {
        String value = "阳肃、来延、翎晟、瑜野、雁帅、商萧、宏欢、松知、海诚、维壮、妙熙、\n" +
                "厚攀、照善、勤育、灿依、观音、曲古、淘廉、铁扬、拂擎、瞻名、易傲、\n" +
                "海戈、游尚、函桦、星幽、昭谦、笃菲、颜瑜、劲传、清庸、清欧、康玮、\n" +
                "誓卫、来歌、国铁、乐均、福宵、余坤、茂华、恭彦、飘璋、倚载、宵淘、\n" +
                "腾浅、玉誓、棋治、阔晨、旭圣、硕方、歌裕、寒满、延淘、吟琪、引湛、\n" +
                "仕卫、霖钟、嘉钦、久坤、齐常、泰运、凡琪、圣尚、妙俊、飞致、利境、\n" +
                "忠豫、锦阳、舟本、泽岳、浩幽、祖帆、章顺、释致、圣论、晨旭、名健、\n" +
                "捷展、越疏、质朴、浚泰、伟俊、宁廉、流奋、庭浩、华临、华质、明瑾、\n" +
                "磊煜、柯鸽、盛萧、尘振、钧恭、宇琪、贵星、至拓、春群、裕朋、飘聚、\n" +
                "翎奇、野岳、飘尚、鸿挚、锐璋、均惜、来苛、保沙、秉阔、豪野、琪引、\n" +
                "建池、璋泽、尊业、舟资、瀚蓝、夫雨、虎棋、泰言、音尊、刊淡、理锋、\n" +
                "民韬、章高、轩挚、锐海、炫疏、喻桐、观毅、论净、流瑜、织杉、熙棕、\n" +
                "煜玉、进合、乾修、皓慎、棕来、阳基、鼎宝、材音、伦维、沧谨、寒锋、\n" +
                "乾庸、春扬、煜彬、亦维、勉谷、鼎昊、镜悟、国沧、博瀚、含诚、洋晓、\n" +
                "善阳、烈昂、若言、遥驰、锐玄、壮逸、雷章、超德、余昭、嘉镇、保秉、\n" +
                "银如、固潭、琪神、天凌、勉惜、西勇、锐毅、兼祖、合善、月雷、忠善、";
        String [] array = value.split("\n");
        List<String> list = new ArrayList<>();
        for (String content : array){
            String arr [] = content.split("、");
            for (String name : arr){
                list.add(name);
            }
        }

        System.out.println(JSON.toJSONString(list));
    }
}
