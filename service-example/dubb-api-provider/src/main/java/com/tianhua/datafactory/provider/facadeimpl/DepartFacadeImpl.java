package com.tianhua.datafactory.provider.facadeimpl;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.DepartFacade;
import com.tianhua.datafactory.dto.DepartmentDTO;
import org.apache.dubbo.config.annotation.DubboService;

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
public class DepartFacadeImpl implements DepartFacade {
    @Override
    public ResultDataDto<List<DepartmentDTO>> searchDepart(Long departId) {
        return null;
    }

    @Override
    public ResultDataDto<Boolean> saveDepart(DepartmentDTO departmentDTO) {
        return null;
    }
}
