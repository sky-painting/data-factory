package com.tianhua.datafactory.convert;

import com.tianhua.datafactory.core.utils.DateUtils;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import com.tianhua.datafactory.vo.datafactory.DataBuildRequestVo;
import com.tianhua.datafactory.vo.model.FieldVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/9
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Mapper
public interface DataFactoryConverter {
    DataFactoryConverter INSTANCE = Mappers.getMapper(DataFactoryConverter.class);


    @Mappings({
            @Mapping(target = "fieldBOList",expression = "java(convert2BO(dataBuildRequestVo.getFieldVOList()))"),
    })
    DataBuildRequestBO convert2BO(DataBuildRequestVo dataBuildRequestVo);

    @Mappings({
            @Mapping(target = "defaultValueList",expression = "java(getDefaultValueList(fieldVO.getDefaultValueList(),fieldVO.getFieldType()))"),
    })
    DataBuildRequestFieldBO convert2BO(FieldVO fieldVO);


    List<DataBuildRequestFieldBO> convert2BO(List<FieldVO> fieldVOList);

    default  List getDefaultValueList(String defaultValueListStr,String type){
        if(defaultValueListStr == null || defaultValueListStr == ""){
            return null;
        }
        List defaultValueList = new ArrayList();
        String []  valueArr = defaultValueListStr.split(",");
        if(JavaFieldTypeEnum.isInt(type)){
            for (String value : valueArr){
                defaultValueList.add(Integer.parseInt(value));
            }
        }else if(JavaFieldTypeEnum.isLong(type)){
            for (String value : valueArr){
                defaultValueList.add(Long.parseLong(value));
            }
        }else if(JavaFieldTypeEnum.isFloat(type)){
            for (String value : valueArr){
                defaultValueList.add(Float.parseFloat(value));
            }
        }
        else if(JavaFieldTypeEnum.isDouble(type)){
            for (String value : valueArr){
                defaultValueList.add(Double.parseDouble(value));
            }
        }
        else if(JavaFieldTypeEnum.isString(type)){
            for (String value : valueArr){
                defaultValueList.add(value);
            }
        }
        //yyyy-MM-dd
        else if(JavaFieldTypeEnum.isDate(type)){
            for (String value : valueArr){
                defaultValueList.add(DateUtils.string2Date(value,"yyyy-MM-dd"));
            }
        }

        //yyyy-MM-dd
        else if(JavaFieldTypeEnum.isDateTime(type)){
            for (String value : valueArr){
                defaultValueList.add(DateUtils.string2Date(value,"yyyy-MM-dd HH:mm:ss"));
            }
        }

        return defaultValueList;

    }


}
