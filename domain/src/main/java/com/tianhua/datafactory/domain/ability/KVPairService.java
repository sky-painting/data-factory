package com.tianhua.datafactory.domain.ability;


import com.tianhua.datafactory.domain.support.kvpair.KVPairBO;

import java.util.List;

/**
 * Description:kv键值对的增删改查操作
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface KVPairService {

    /**
     * 获取所有kv对象
     * @return
     */
     List<KVPairBO> getAll();

    /**
     * 通过kv模型的条件获取一个kv对象实例
     * @param kv
     * @return
     */
    KVPairBO get(KVPairBO kv);

    /**
     * 通过kv模型的条件获取多个个kv对象实例
     * @param kv
     * @return
     */
    List<KVPairBO> getList(KVPairBO kv);


    /**
     * 插入一个kv对象
     * @param kv
     * @return
     */
    boolean insert(KVPairBO kv);

    /**
     * 批量插入
     * @param kvList
     * @return
     */
    boolean batchInsert(List<KVPairBO> kvList);

    /**
     * 通过多个kv条件获取kv对象实例
     * @param kvList
     * @return
     */
    List<KVPairBO> getByKey(List<KVPairBO> kvList);

    /**
     * 修改kv
     * @param kv
     * @return
     */
    boolean update(KVPairBO kv);

    /**
     * 根据key实例删除
     * @param kv
     * @return
     */
    boolean delete(KVPairBO kv);


    /**
     * 判断kv是否存在
     * @param kv
     * @return
     */
    boolean contains(KVPairBO kv);

}
