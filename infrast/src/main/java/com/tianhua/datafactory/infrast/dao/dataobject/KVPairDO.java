package com.tianhua.datafactory.infrast.dao.dataobject;

import lombok.Data;

/**
 * Description:数据库实体
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
@Data
public class KVPairDO {
    private Long id;
    private String key;
    private String value;
    private String groupKey;
    private String parentKey;

    /**
     * 关联了哪个对象
     */
    private String referKey;

    /**
     * 关联对象的标示
     */
    private String referId;

    /**
     * value值类型
     */
    private Integer valueType;


    /**
     * key值类型
     */
    private Integer keyType;

    /**
     * json格式的对象存储
     */
    private String valueJson;

    /**
     * kv状态，跟着主对象走
     */
    private Integer status;

}
