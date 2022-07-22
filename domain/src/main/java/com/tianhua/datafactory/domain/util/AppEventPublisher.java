package com.tianhua.datafactory.domain.util;

import com.tianhua.datafactory.domain.event.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppEventPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发送spring容器事件入口方法
     */
    public <T extends BaseEvent> void publish(T t) {
        applicationContext.publishEvent(t);
    }

}