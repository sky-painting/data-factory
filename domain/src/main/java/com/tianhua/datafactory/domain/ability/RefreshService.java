package com.tianhua.datafactory.domain.ability;

import com.tianhua.datafactory.domain.GlobalConstant;
import com.tianhua.datafactory.domain.bo.bean.PlantUMLApiContextBean;
import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.domain.bo.model.ParamModelBO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:对扩展属性进行校验刷新
 * date: 2022/4/28
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
public class RefreshService {
    /**
     * 刷新属性内容，对apiParam进行属性区分
     * @param plantUMLApiContextBean
     */
    public void refresh(PlantUMLApiContextBean plantUMLApiContextBean){
        //将扩展字段内容提取出来
        plantUMLApiContextBean.getParamClassBeanList().forEach(paramBean -> {
            List<FieldBO> fieldList = paramBean.getFieldBeanList().stream().filter(fieldBean -> !fieldBean.isExtendFieldTag()).collect(Collectors.toList());
            paramBean.setFieldBeanList(fieldList);
        });

        Map<String, ParamModelBO> paramBeanMap = plantUMLApiContextBean.getParamClassBeanList().stream().collect(Collectors.toMap(ParamModelBO::getParamClassName, o -> o));


        /**
         * 针对提取的扩展字段信息更新参数模型
         */
        plantUMLApiContextBean.getApiBeanList().forEach(apiBean -> {
            ParamModelBO returnParamBean = apiBean.getReturnParamModel();
            if(returnParamBean != null){
                ParamModelBO newReturnParamBean = getParamModel(returnParamBean.getParamClassName(), plantUMLApiContextBean);
                if(newReturnParamBean != null){
                    apiBean.setReturnParamModel(newReturnParamBean);
                }
            }

            List<ParamModelBO> paramBeanList = apiBean.getParamList();
            List<ParamModelBO> newParamBeanList = new ArrayList<>();
            for (ParamModelBO oldParam : paramBeanList){
                ParamModelBO newParam = paramBeanMap.get(oldParam.getParamClassName());
                if(newParam != null){
                    oldParam.setFieldBeanList(newParam.getFieldBeanList());
                    newParamBeanList.add(oldParam);
                }else {
                    newParamBeanList.add(oldParam);
                }
            }
            apiBean.setParamList(newParamBeanList);
        });
    }

    /**
     * 根据api返回的参数来获取返回参数对象模型
     * @param paramClassName
     * @param plantUMLApiContextBean
     * @return
     */
    private ParamModelBO getParamModel(String paramClassName,PlantUMLApiContextBean plantUMLApiContextBean){
        if(paramClassName.contains("<")){
            if(paramClassName.endsWith(">")){
                paramClassName = paramClassName.replace(">","");
            }
            String [] paramClassArr = paramClassName.split("<");
            paramClassName = paramClassArr[1];
        }

        if(paramClassName.toLowerCase().endsWith(GlobalConstant.DTO)
        || paramClassName.toLowerCase().endsWith(GlobalConstant.VO)){
            String finalParamClassName = paramClassName;
            List<ParamModelBO> paramBeanList = plantUMLApiContextBean.getParamClassBeanList().stream().filter(paramBean -> paramBean.getParamClassName().equals(finalParamClassName)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(paramBeanList)){
                return paramBeanList.get(0);
            }
        }
        return null;
    }
}
