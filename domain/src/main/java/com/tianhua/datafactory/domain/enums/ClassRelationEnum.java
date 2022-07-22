package com.tianhua.datafactory.domain.enums;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Description:
 * date: 2021/6/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public enum ClassRelationEnum {

    /**
     * GENERALIZATION
     */
    GENERALIZATION("泛化-继承", Sets.newHashSet("<|--","--|>")),
    REALIZE("实现",Sets.newHashSet("..|>","<|..")),
    ;

    private String desc;
    private Set<String> tagSet;
    ClassRelationEnum(String desc, Set<String> tagSet){
        this.desc = desc;
        this.tagSet = tagSet;
    }

    public String getDesc() {
        return desc;
    }

    public Set<String> getTagSet() {
        return tagSet;
    }

    /**
     * 解析类间关系
     * @param content
     * @return
     */
    public static Map<String, String> parseRelation(String content){
        AtomicReference<ClassRelationEnum> classRelationEnum = new AtomicReference<>();
        for (ClassRelationEnum relationEnum : ClassRelationEnum.values()){
            relationEnum.getTagSet().forEach(tag->{
                if(content.contains(tag)){
                    classRelationEnum.set(relationEnum);
                }
            });
        }
        ClassRelationEnum relationEnum = classRelationEnum.get();
        if(relationEnum == null){
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String className = getChild(content);
        if (!StringUtils.isEmpty(className)) {
            map.put("class",className);
        }
        if(relationEnum.name().equals(ClassRelationEnum.GENERALIZATION.name())){
            map.put("relation"," extends "+getSuper(content));
        }
        if(relationEnum.name().equals(ClassRelationEnum.REALIZE.name())){
            map.put("relation"," implements "+getSuper(content));
        }

        return map;
    }

    /**
     * 获取父类或者接口
     * @param content
     * @return
     */
    private static String getSuper(String content){
        if(content.contains("<|--")){
           return content.trim().split("<\\|--")[0];
        }
        if(content.contains("--|>")){
            return content.trim().split("--\\|>")[1];
        }
        if(content.contains("..|>")){
            return content.trim().split("..\\|>")[1];
        }
        if(content.contains("<|..")){
            return content.trim().split("<\\|..")[0];
        }
        return null;
    }


    /**
     * 获取父类或者接口
     * @param content
     * @return
     */
    private static String getChild(String content){
        if(content.contains("<|--")){
            return content.trim().split("<\\|--")[1];
        }
        if(content.contains("--|>")){
            return content.trim().split("--\\|>")[0];
        }
        if(content.contains("..|>")){
            return content.trim().split("..\\|>")[0];
        }
        if(content.contains("<|..")){
            return content.trim().split("<\\|..")[1];
        }
        return null;
    }

}
