package com.tianhua.datafactory.client.function;

/**
 * Description
 * 缓存函数
 * date: 2022/8/9
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface CacheFunction extends Function{

    /**
     * 构建count条缓存记录
     * 实现需要加锁
     * @param count 缓存条数
     */
    void buildCache(Integer count) throws Exception;


}
