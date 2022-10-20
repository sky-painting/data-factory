package com.tianhua.datafactory.domain.ability;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.model.ModelSuffixConfigBO;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import com.tianhua.datafactory.domain.repository.ModelQueryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Description
 * date: 2022/8/28
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class GenericService {

    @Autowired
    private ModelQueryRepository modelQueryRepository;


    /**
     * 获取属性类型中的泛型对象
     *
     * List<XxxBO>
     * List<List<XxxBO>>
     * @param fieldType
     * @return
     */
    public GenericTypeBO getGenericType(String fieldType){
        GenericTypeBO genericTypeBO = new GenericTypeBO();

        if(fieldType.contains("[]")){
            genericTypeBO.setRealType(fieldType.replace("[]","").trim());
            genericTypeBO.setWrapType("Array");
            return genericTypeBO;
        }

        if(!fieldType.contains("<")){
            genericTypeBO.setRealType(fieldType);
            return genericTypeBO;
        }


        if(fieldType.contains(",")){
            String [] fieldTypeArr = fieldType.split(",");
            String keyWrapper = fieldTypeArr[0];
            String valueWrapper = fieldTypeArr[1];
            genericTypeBO.setWrapType(keyWrapper.split("<")[0]);
            genericTypeBO.setRealKeyType(keyWrapper.split("<")[1]);
            if(valueWrapper.contains(">>")){
                genericTypeBO.setRealValueType(valueWrapper.replace(">>", ">"));
            }else {
                genericTypeBO.setRealValueType(valueWrapper.replace(">", ""));

            }
            return genericTypeBO;
        }

        String [] array = fieldType.split("<");
        if(array.length == 2){
            genericTypeBO.setWrapType(array[0]);
            genericTypeBO.setRealType(array[1].replace(">",""));
            return genericTypeBO;
        }

        if(array.length == 3){
            GenericTypeBO realTypeBO = new GenericTypeBO();
            realTypeBO.setRealType(array[2].replace(">",""));
            realTypeBO.setWrapType(array[1]);
            GenericTypeBO superTypeBO = new GenericTypeBO();
            superTypeBO.setRealType(array[1] + "<" + array[2].replace(">","")+">");
            superTypeBO.setWrapType(array[1]);
            Map<String, GenericTypeBO> genericTypeBOMap = new HashMap<>();
            genericTypeBOMap.put("realType",realTypeBO);
            superTypeBO.setSubGenericTypeMap(genericTypeBOMap);
            return superTypeBO;
        }

        return genericTypeBO;
    }

    /**
     * 获取属性类型中的泛型对象
     *
     * List<XxxBO>
     * List<List<XxxBO>>
     * @param fieldType
     * @return
     */
    public GenericTypeBO getGenericTypeWrapper(String fieldType){
        GenericTypeBO genericTypeBO = getGenericType(fieldType);
        List<ModelSuffixConfigBO>  modelSuffixConfigBOList = modelQueryRepository.getModelSuffixConfigList();
        Optional<ModelSuffixConfigBO> optional = modelSuffixConfigBOList.stream().filter(modelSuffixConfigBO -> StringUtils.isNotEmpty(genericTypeBO.getRealType()) && genericTypeBO.getRealType().endsWith(modelSuffixConfigBO.getSuffix())).findAny();
        if(optional.isPresent()){
            genericTypeBO.setRealTypeModel(true);
        }

        return genericTypeBO;
    }



    /**
     * 检查属性是否是模型属性
     * @param dataBuildRequestFieldBO
     * @return
     */
    public boolean checkModelClass(DataBuildRequestFieldBO dataBuildRequestFieldBO){

        List<ModelSuffixConfigBO>  modelSuffixConfigBOList = modelQueryRepository.getModelSuffixConfigList();

        dataBuildRequestFieldBO.setRealFieldType(dataBuildRequestFieldBO.getFieldType());

        if(CollectionUtils.isEmpty(modelSuffixConfigBOList)){
            return false;
        }

        GenericTypeBO genericTypeBO = getGenericType(dataBuildRequestFieldBO.getRealFieldType());

        if(StringUtils.isNotEmpty(genericTypeBO.getRealType())){
            dataBuildRequestFieldBO.setRealFieldType(genericTypeBO.getRealType());
        }

        else if(StringUtils.isNotEmpty(genericTypeBO.getRealValueType())){
            dataBuildRequestFieldBO.setRealFieldType(genericTypeBO.getRealValueType());
        }

        Optional<ModelSuffixConfigBO> optional = modelSuffixConfigBOList.stream().filter(modelSuffixConfigBO -> StringUtils.isNotEmpty(genericTypeBO.getRealType()) && genericTypeBO.getRealType().endsWith(modelSuffixConfigBO.getSuffix())).findAny();
        if(optional.isPresent()){
            genericTypeBO.setRealTypeModel(true);
            return true;
        }

        if(StringUtils.isNotEmpty(genericTypeBO.getRealValueType())){
            optional = modelSuffixConfigBOList.stream().filter(modelSuffixConfigBO -> genericTypeBO.getRealValueType().endsWith(modelSuffixConfigBO.getSuffix())).findAny();
            if(optional.isPresent()){
                genericTypeBO.setRealValueTypeModel(true);
                return true;
            }
        }
        return false;
    }


    public Object buildGenericData(String wrapClass, List<Map<String, Object>> list) {
        if(JavaFieldTypeEnum.isList(wrapClass)){
            return list;
        }
        if(JavaFieldTypeEnum.isSet(wrapClass)){
            return Sets.newHashSet(list);
        }

        if(JavaFieldTypeEnum.isArray(wrapClass)){
            Object [] array = new Object[list.size()];
            for (int i = 0;i < array.length;i++){
                array[i] = list.get(i);
            }
            return array;
        }

        return list;
    }




    public static void main(String[] args) {
        GenericService genericService = new GenericService();
        System.out.println(JSON.toJSONString(genericService.getGenericType("Integer []")));
        System.out.println(JSON.toJSONString(genericService.getGenericType("String []")));

        System.out.println(JSON.toJSONString(genericService.getGenericType("List<XxxBO>")));
        System.out.println(JSON.toJSONString(genericService.getGenericType("List<List<List<List<XxxBO>>>>")));

        System.out.println(JSON.toJSONString(genericService.getGenericType("Map<String,XxxBO>")));
        System.out.println(JSON.toJSONString(genericService.getGenericType("Map<String,List<XxxBO>>")));

    }

}
