package com.tianhua.springboot.controller;

import com.coderman.utils.response.ResultDataDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/1
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RestController
public class DataController {
    @RequestMapping(value = "getlistdto")
    public ResultDataDto getListDto(){
        ResultDataDto resultDataDto = new ResultDataDto();

        List list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("e");
        list.add("f");

        resultDataDto.setData(list);
        return resultDataDto;
    }

    @RequestMapping(value = "getlist")
    public List getList(){
        List list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("e");
        list.add("f");

        return list;
    }
}
