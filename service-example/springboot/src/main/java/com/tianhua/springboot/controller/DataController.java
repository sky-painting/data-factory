package com.tianhua.springboot.controller;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.springboot.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DataController {

    @RequestMapping(value = "getlistobjectdto")
    public ResultDataDto getListDto(){
        ResultDataDto resultDataDto = new ResultDataDto();

        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO("zhangsan","张三ss","中国1"));
        list.add(new UserDTO("lisi","李四","中国2"));
        list.add(new UserDTO("wangwu","王五","中国3"));
        list.add(new UserDTO("zhouxxsi","周四","中国4"));
        list.add(new UserDTO("zhangxxsaxn","张三xxss","中国5"));
        list.add(new UserDTO("zhangsaxxn","张xx三ss","中国6"));
        list.add(new UserDTO("zhangxxsaxn","张三xxss","中国5"));
        for (int i =0 ;i < 1000; i++){
            list.add(new UserDTO("wangwu"+i,"王五"+i,"中国3"+i));
        }
        resultDataDto.setData(list);
        return resultDataDto;
    }



    @RequestMapping(value = "getlistobjectdtoparam")
    public ResultDataDto getListDtoParam(@RequestParam(value = "page") Integer page,@RequestParam(value = "pageSize") Integer pageSize ){
        log.info("page = {},pageSize = {}",page,pageSize);
        ResultDataDto resultDataDto = new ResultDataDto();

        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO("zhangsan","张三ss","中国1"));
        list.add(new UserDTO("lisi","李四","中国2"));
        list.add(new UserDTO("wangwu","王五","中国3"));
        list.add(new UserDTO("zhouxxsi","周四","中国4"));
        list.add(new UserDTO("zhangxxsaxn","张三xxss","中国5"));
        list.add(new UserDTO("zhangsaxxn","张xx三ss","中国6"));
        list.add(new UserDTO("zhangxxsaxn","张三xxss","中国5"));
        for (int i =0 ;i < 1000; i++){
            list.add(new UserDTO("wangwu"+i,"王五"+i,"中国3"+i));
        }
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
