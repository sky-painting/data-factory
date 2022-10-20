package com.tianhua.datafactory.infrast.dataconvert;

import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;
import com.tianhua.datafactory.infrast.dao.dataobject.KVPairDO;
import org.mapstruct.Mapper;
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
public interface KVPairModelConvert {
    KVPairModelConvert INSTANCE = Mappers.getMapper(KVPairModelConvert.class);

    /**
     *
     * @Description:
     * @return FieldBO
     */
    KVPairBO do2bo(KVPairDO kvPairDO);
    /**
     *
     * @Description:
     * @return List<KVPairDO>
     */
    List<KVPairBO> doList2boList(List<KVPairDO> kvPairDOList);
    /**
     *
     * @Description:
     * @return KVPairDO
     */
    KVPairDO bo2do(KVPairBO kvPairBO);
    /**
     *
     * @Description:
     * @return List<FieldModelDO>
     */
    List<KVPairDO> boList2doList(List<KVPairBO> kvPairBOS);
}
