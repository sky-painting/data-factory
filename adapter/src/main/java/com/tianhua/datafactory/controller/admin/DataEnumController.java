package com.tianhua.datafactory.controller.admin;

import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.domain.enums.ReturnWrapClassEnum;
import com.tianhua.datafactory.controller.BaseController;
import com.tianhua.datafactory.domain.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * date: 2022/5/31
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class DataEnumController extends BaseController {

    /**
     * @param enumCode
     * @return ResultDataDto 构建结果
     * @Description:根据数据源构建数据
     * @version v1.0
     */
    @RequestMapping(value = "/dataEnum/{enumCode}", method = RequestMethod.GET)
    public ResultDataDto generate(@PathVariable(value = "enumCode") String enumCode) {
        if(ApiTypeEnum.isApiType(enumCode)){
            return ResultDataDto.success(ApiTypeEnum.getOptionList());
        }

        if(DataSourceTypeEnum.isDataSourceType(enumCode)){
            return ResultDataDto.success(DataSourceTypeEnum.getOptionList());
        }

        if(ModelTypeEnum.isModelType(enumCode)){
            return ResultDataDto.success(ModelTypeEnum.getOptionList());
        }

        if(ModelValueMappingType.isModelValueMapping(enumCode)){
            return ResultDataDto.success(ModelValueMappingType.getOptionList());
        }

        if(VisitStrategyEnums.isVisitStrategy(enumCode)){
            return ResultDataDto.success(VisitStrategyEnums.getOptionList());
        }

        if(MethodTypeEnum.isMethodType(enumCode)){
            return ResultDataDto.success(MethodTypeEnum.getOptionList());
        }
        if(RegistServerEnum.isRegistServer(enumCode)){
            return ResultDataDto.success(RegistServerEnum.getOptionList());
        }

        if(ReturnWrapClassEnum.isReturnWrapClass(enumCode)){
            return ResultDataDto.success(ReturnWrapClassEnum.getOptionList());
        }

        if(ApiModelFieldStatusEnum.isApiModelFieldStatus(enumCode)){
            return ResultDataDto.success(ApiModelFieldStatusEnum.getOptionList());
        }
        return ResultDataDto.success();
    }




}
