package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.domain.bo.model.FieldBO;
import com.tianhua.datafactory.infrast.dao.dataobject.FieldModelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Description:
 * date: 2022/5/31
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Mapper
public interface FieldModelConvert {
    FieldModelConvert INSTANCE = Mappers.getMapper(FieldModelConvert.class);

    /**
     *
     * @Description:
     * @return FieldBO
     */
    @Mappings({
            @Mapping(target = "fieldExtBO",expression = "java(org.apache.commons.lang3.StringUtils.isEmpty(fieldModelDO.getFieldExtJsonStr()) ? new com.tianhua.datafactory.domain.bo.model.FieldExtBO() : com.alibaba.fastjson.JSON.parseObject(fieldModelDO.getFieldExtJsonStr(),com.tianhua.datafactory.domain.bo.model.FieldExtBO.class))"),
    })
    FieldBO do2bo(FieldModelDO fieldModelDO);
    /**
     *
     * @Description:
     * @return List<FieldBO>
     */
    List<FieldBO> doList2boList(List<FieldModelDO> fieldModelDOS);
    /**
     *
     * @Description:
     * @return FieldModelDO
     */
    @Mappings({
            @Mapping(target = "fieldExtJsonStr",expression = "java(com.alibaba.fastjson.JSON.toJSONString(fieldBO.getFieldExtBO()))"),
    })
    FieldModelDO bo2do(FieldBO fieldBO);
    /**
     *
     * @Description:
     * @return List<FieldModelDO>
     */
    List<FieldModelDO> boList2doList(List<FieldBO> fieldBOList);
}
