package com.tianhua.datafactory.provider.facadeimpl;

import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.DepartFacade;
import com.tianhua.datafactory.dto.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/14
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@DubboService
@Slf4j
public class DepartFacadeImpl implements DepartFacade {
    @Override
    public ResultDataDto<List<DepartmentDTO>> searchDepart(Long departId) {
        log.info("departId = {}", departId);
        return ResultDataDto.success(getDepartmentList());
    }

    @Override
    public ResultDataDto<Boolean> saveDepart(DepartmentDTO departmentDTO) {
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
