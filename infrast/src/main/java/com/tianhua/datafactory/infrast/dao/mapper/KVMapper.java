package com.tianhua.datafactory.infrast.dao.mapper;


import com.tianhua.datafactory.infrast.dao.dataobject.KVPairDO;

import java.util.List;

/**
 * Description:
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface KVMapper {
    /**
     * 插入
     * @param kvPairDO
     * @return
     */
    int insert(KVPairDO kvPairDO);

    /**
     * 插入
     * @param list
     * @return
     */
    int insertBatch(List<KVPairDO> list);



    /**
     * 根据条件查询单条数据
     * @param kvPairDO
     * @return
     */
    KVPairDO selectOne(KVPairDO kvPairDO);

    /**
     * 判断是否存在kv信息
     * @param kvPairDO
     * @return
     */
    int count(KVPairDO kvPairDO);

    /**
     * 根据条件修改v
     * @param kvPairDO
     * @return
     */
    int update(KVPairDO kvPairDO);


    /**
     * 根据条件查询多条记录
     * @param kvPairDO
     * @return
     */
    List<KVPairDO> selectList(KVPairDO kvPairDO);


    List<KVPairDO> selectAll();

    /**
     * 建议使用逻辑删除
     * @param kvPairDO
     * @return
     */
    int delete(KVPairDO kvPairDO);

}
