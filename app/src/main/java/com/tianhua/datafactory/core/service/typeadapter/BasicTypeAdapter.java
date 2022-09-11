package com.tianhua.datafactory.core.service.typeadapter;

import com.tianhua.datafactory.core.service.DataTypeAdapter;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * 处理基本数据的类型适配
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "basicTypeAdapter")
public class BasicTypeAdapter implements DataTypeAdapter {

    @Resource(name = "objectTypeAdapter")
    private DataTypeAdapter objectTypeAdapter;

    @Override
    public List<DataBuildRequestFieldBO> buildFieldList(GenericTypeBO genericTypeBO) throws Exception {
        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();
        DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO<>();

        if(!JavaFieldTypeEnum.isBasicType(genericTypeBO.getRealType())){
            return objectTypeAdapter.buildFieldList(genericTypeBO);
        }

        dataBuildRequestFieldBO.setFieldType(genericTypeBO.getRealType());

        if(genericTypeBO.getApiBO().getReturnParamModel() == null){
            dataBuildRequestFieldBO.setFieldName("result");
        }else {
            dataBuildRequestFieldBO.setFieldName(genericTypeBO.getApiBO().getReturnParamModel().getParamVarName());
            dataBuildRequestFieldBO.setDataSourceCode(genericTypeBO.getApiBO().getReturnParamModel().getDataSourceCode());
            dataBuildRequestFieldBO.setBuildRuleDSL(genericTypeBO.getApiBO().getReturnParamModel().getBuildRuleDSL());
            if(StringUtils.isNotEmpty(genericTypeBO.getApiBO().getReturnParamModel().getDefaultValueList())){
                buildDefaultValueList(genericTypeBO, dataBuildRequestFieldBO, genericTypeBO.getApiBO().getReturnParamModel().getDefaultValueList());
            }
        }
        fieldBOList.add(dataBuildRequestFieldBO);
        return fieldBOList;
    }

    private void buildDefaultValueList(GenericTypeBO genericTypeBO, DataBuildRequestFieldBO dataBuildRequestFieldBO, String defaultValueList){
        List valueList = new ArrayList();

        if(StringUtils.isEmpty(defaultValueList)){
            return;
        }

        String [] arrayList = defaultValueList.split(",");
        if(JavaFieldTypeEnum.isInt(genericTypeBO.getRealType())){
            for (String value : arrayList){
                valueList.add(Integer.parseInt(value));
            }
        }

        if(JavaFieldTypeEnum.isLong(genericTypeBO.getRealType())){
            for (String value : arrayList){
                valueList.add(Long.parseLong(value));
            }
        }

        if(JavaFieldTypeEnum.isBoolean(genericTypeBO.getRealType())){
            for (String value : arrayList){
                valueList.add(Boolean.parseBoolean(value));
            }
        }

        if(JavaFieldTypeEnum.isShort(genericTypeBO.getRealType())){
            for (String value : arrayList){
                valueList.add(Short.parseShort(value));
            }
        }
        if(JavaFieldTypeEnum.isString(genericTypeBO.getRealType())){
            for (String value : arrayList){
                valueList.add(value);
            }
        }
        dataBuildRequestFieldBO.setDefaultValueList(valueList);

    }
}
