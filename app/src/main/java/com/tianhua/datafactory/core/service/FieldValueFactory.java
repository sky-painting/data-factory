package com.tianhua.datafactory.core.service;

import com.alibaba.fastjson.JSON;
import com.tianhua.datafactory.domain.ability.GenericService;
import com.tianhua.datafactory.domain.bo.GenericTypeBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataBuildRequestFieldBO;
import com.tianhua.datafactory.domain.bo.datafactory.DataSourceFieldRequestBean;
import com.tianhua.datafactory.domain.enums.JavaFieldTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.*;

/**
 * Description
 * date: 2022/8/12
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class FieldValueFactory {

    @Resource(name = "dataGenerateDefaultServiceImpl")
    private DataGenerateService dataGenerateDefaultServiceImpl;

    @Autowired
    private GenericService genericService;


    @Autowired
    private DataGenerateServiceFactory dataGenerateServiceFactory;

    private static SecureRandom secureRandom = new SecureRandom();


    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    public Object getFieldValue(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldBO dataBuildRequestFieldBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();

        String dataSourceCode = dataBuildRequestFieldBO.getDataSourceCode();

        //从默认值中获取数据
        if (StringUtils.isEmpty(dataSourceCode)
                || CollectionUtils.isNotEmpty(dataBuildRequestFieldBO.getDefaultValueList())) {
            return dataGenerateDefaultServiceImpl.getRandomData(dataSourceFieldRequestBean);
        }

        if(dataBuildRequestFieldBO.getDataSourceType() == null){
            log.warn("数据源类型为空,dataBuildRequestFieldBO = {}", JSON.toJSONString(dataBuildRequestFieldBO));
            return null;
        }

        DataGenerateService dataGenerateService = dataGenerateServiceFactory.getDataGenerateService(dataBuildRequestFieldBO.getDataSourceType().intValue());

        return dataGenerateService.getRandomData(dataSourceFieldRequestBean);

    }


    /**
     * 根据请求的数据上下文生成随机的数据字段值
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    public Object getFieldValueWrapper(DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        DataBuildRequestFieldBO dataBuildRequestFieldBO = dataSourceFieldRequestBean.getDataBuildRequestFieldBO();
        int randomCount = secureRandom.nextInt(10);

        GenericTypeBO genericTypeBO = genericService.getGenericType(dataBuildRequestFieldBO.getFieldType());
        //外部是list类型，内部是java基本类型，则随机生成一定数量的数据当做集合数据
        if(JavaFieldTypeEnum.isList(genericTypeBO.getWrapType()) && JavaFieldTypeEnum.isBasicType(genericTypeBO.getRealType())){
            List list = new ArrayList<>();
            for (int i = 0;i < randomCount;i ++){
                list.add(getFieldValue(dataSourceFieldRequestBean));
            }
            return list;
        }

        //外部是set类型，内部是java基本类型，则随机生成一定数量的数据当做集合数据
        if(JavaFieldTypeEnum.isSet(genericTypeBO.getWrapType()) && JavaFieldTypeEnum.isBasicType(genericTypeBO.getRealType())){
            Set set = new HashSet();
            for (int i = 0;i < randomCount;i ++){
                set.add(getFieldValue(dataSourceFieldRequestBean));
            }
            return set;
        }

        //外部是数组类型，内部是java基本类型，则随机生成一定数量的数据当做集合数据
        if(JavaFieldTypeEnum.isArray(genericTypeBO.getWrapType()) && JavaFieldTypeEnum.isBasicType(genericTypeBO.getRealType())){
            if(JavaFieldTypeEnum.isInt(genericTypeBO.getRealType())){
                return buildIntegerArray(randomCount, dataSourceFieldRequestBean);
            }

            if(JavaFieldTypeEnum.isLong(genericTypeBO.getRealType())){
                return buildLongArray(randomCount, dataSourceFieldRequestBean);
            }

            if(JavaFieldTypeEnum.isString(genericTypeBO.getRealType())){
                return buildStringArray(randomCount, dataSourceFieldRequestBean);
            }

            if(JavaFieldTypeEnum.isDate(genericTypeBO.getRealType())){
                return buildDateArray(randomCount, dataSourceFieldRequestBean);
            }
        }

        //List<XxxBO>
        if(JavaFieldTypeEnum.isList(genericTypeBO.getWrapType()) && genericTypeBO.isRealTypeModel()) {
            List list = new ArrayList<>();
            for (int i = 0;i < randomCount;i ++){
                list.add(getFieldValue(dataSourceFieldRequestBean));
            }
            return list;
        }

        //Set<XxxBO>
        if(JavaFieldTypeEnum.isSet(genericTypeBO.getWrapType()) && genericTypeBO.isRealTypeModel()) {
            Set set = new HashSet();
            for (int i = 0;i < randomCount;i ++){
                set.add(getFieldValue(dataSourceFieldRequestBean));
            }
            return set;
        }


        return getFieldValue(dataSourceFieldRequestBean);
    }


    /**
     * integer 数组
     * @param randomCount
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object buildIntegerArray(int randomCount, DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        Integer [] array = new Integer[randomCount];
        for (int i = 0;i < randomCount;i ++){
            array[i] = Integer.parseInt(getFieldValue(dataSourceFieldRequestBean).toString());
        }
        return array;
    }



    /**
     * Long 数组
     * @param randomCount
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object buildLongArray(int randomCount, DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        Long [] array = new Long[randomCount];
        for (int i = 0;i < randomCount;i ++){
            array[i] = Long.parseLong(getFieldValue(dataSourceFieldRequestBean).toString());
        }
        return array;
    }


    /**
     * Long 数组
     * @param randomCount
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object buildStringArray(int randomCount, DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        String [] array = new String[randomCount];
        for (int i = 0;i < randomCount;i ++){
            array[i] = getFieldValue(dataSourceFieldRequestBean).toString();
        }
        return array;
    }


    /**
     * Long 数组
     * @param randomCount
     * @param dataSourceFieldRequestBean
     * @return
     * @throws Exception
     */
    private Object buildDateArray(int randomCount, DataSourceFieldRequestBean dataSourceFieldRequestBean) throws Exception {
        Date [] array = new Date[randomCount];
        for (int i = 0;i < randomCount;i ++){
            array[i] = (Date)getFieldValue(dataSourceFieldRequestBean);
        }
        return array;
    }


}
