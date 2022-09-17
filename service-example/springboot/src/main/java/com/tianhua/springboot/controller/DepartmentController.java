package com.tianhua.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.springboot.dto.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/16
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class DepartmentController {

    @RequestMapping(value = "/depart/search/{departId}")
    public ResultDataDto<List<DepartmentDTO>> searchDepart(@PathVariable(value = "departId") Long departId){
        log.info("departId = {}", departId);
        return ResultDataDto.success(getDepartmentList());
    }


    @RequestMapping(value = "/depart/save", method = RequestMethod.POST)
    public ResultDataDto<Boolean> saveDepart(@RequestBody DepartmentDTO departmentDTO){
        log.info("departmentDTO = {}", JSON.toJSONString(departmentDTO));
        return ResultDataDto.success(true);
    }


    /**
     * demo 数据
     * @return
     */
    private List<DepartmentDTO> getDepartmentList(){
        List<DepartmentDTO> list = new ArrayList<>();

        for (int i = 0;i < 10;i ++){
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setDepartName("技术部" + i);
            departmentDTO.setManagerId(10L + i);
            departmentDTO.setGrade(i+1);
            departmentDTO.setSuperId(1L + i);
            departmentDTO.setId(0L + i);
            list.add(departmentDTO);
        }

        return list;
    }


}
