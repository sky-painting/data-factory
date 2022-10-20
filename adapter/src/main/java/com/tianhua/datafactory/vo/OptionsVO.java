package com.tianhua.datafactory.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * date: 2022/5/17
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class OptionsVO {

    private List<Map<String,String>> options = new ArrayList<>();

    public void addOptionItem(String label,String value){
        Map<String,String> optionItem = new HashMap<>();
        optionItem.put("label",label);
        optionItem.put("value",value);
        this.options.add(optionItem);
    }



}
