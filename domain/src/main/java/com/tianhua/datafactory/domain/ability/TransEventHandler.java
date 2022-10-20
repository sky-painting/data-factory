package com.tianhua.datafactory.domain.ability;


import com.tianhua.datafactory.domain.event.BaseEvent;

/**
 * Description:事务处理接口
 * date: 2022/1/8
 *
 * @author shenshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public interface TransEventHandler<T extends BaseEvent> {
    /**
     * 抽象事务处理接口
     * 实现此接口的类方法上需要加
     * @TransactionalEventListener(fallbackExecution = true)
     *
     * @param baseEvent
     */
    void handleTransEvent(T baseEvent);
}
