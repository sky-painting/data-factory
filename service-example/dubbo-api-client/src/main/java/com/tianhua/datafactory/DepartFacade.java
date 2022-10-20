package com.tianhua.datafactory;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.dto.DepartmentDTO;

import java.util.List;

/**
 * Description
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface DepartFacade {
    /**
     * 获取部门数据信息
     *
     * 将此接口注册为数据源接口
     * 并从此接口获取部门数据
     * @param departId
     * @return
     */
    ResultDataDto<List<DepartmentDTO>> searchDepart(Long departId);


    /**
     * 保存部门数据信息
     *
     * 模拟数据入参mock，或者直接调用
     * @param departmentDTO
     * @return
     */
    ResultDataDto<Boolean> saveDepart(DepartmentDTO departmentDTO);


}
