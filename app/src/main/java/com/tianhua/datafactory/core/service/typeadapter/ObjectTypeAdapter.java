package com.tianhua.datafactory.core.service.typeadapter;

import com.tianhua.datafactory.core.service.DataTypeAdapter;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * date: 2022/9/11
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service(value = "objectTypeAdapter")
public class ObjectTypeAdapter implements DataTypeAdapter{
    @Autowired
    private ModelQueryRepository modelQueryRepository;

    @Override
    public List<DataBuildRequestFieldBO> buildFieldList(GenericTypeBO genericTypeBO) throws Exception {
        List<FieldBO> fieldModelBOList = modelQueryRepository.getModelField(genericTypeBO.getApiBO().getProjectCode(), genericTypeBO.getRealType());
        if(CollectionUtils.isEmpty(fieldModelBOList)){
            throw new Exception("找不到出参对应的对象模型,检查接口声明或者新增对应接口返回模型信息");
        }

        List<DataBuildRequestFieldBO> fieldBOList = new ArrayList<>();
        for (FieldBO fieldBO : fieldModelBOList){
            DataBuildRequestFieldBO dataBuildRequestFieldBO = new DataBuildRequestFieldBO<>();
            dataBuildRequestFieldBO.setFieldType(fieldBO.getFieldType());
            dataBuildRequestFieldBO.setFieldName(fieldBO.getFieldName());
            dataBuildRequestFieldBO.setDataSourceCode(fieldBO.getFieldExtBO().getDataSourceCode());
            dataBuildRequestFieldBO.setDefaultValueList(fieldBO.getFieldExtBO().getDefaultValueList());
            dataBuildRequestFieldBO.setBuildRuleDSL(fieldBO.getFieldExtBO().getBuildRuleDSL());
            fieldBOList.add(dataBuildRequestFieldBO);
        }
        return fieldBOList;
    }

}
