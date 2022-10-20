package com.tianhua.datafactory.advisor;


import com.alibaba.fastjson.JSON;
import com.coderman.utils.response.ResultDataDto;
import com.tianhua.datafactory.vo.ResultAmisVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Aspect
@Slf4j
public class ControllerAdvisor {
    @Around(value = "execution( public * com.tianhua.datafactory.controller.admin..*(..))")
    public Object transferException(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        try {
            log.info("{}.{} param:{}", method.getDeclaringClass().getName(), method.getName(), JSON.toJSONString(joinPoint.getArgs()));
            Object result = joinPoint.proceed();
            log.info("{}.{} result:{}", method.getDeclaringClass().getName(), method.getName(), JSON.toJSONString(result));
            return result;
        } catch (Throwable exception) {
            if (!method.getReturnType().getName().equals(ResultDataDto.class.getName())) {
                log.error("sdfadsfadsfasdfxxx.........");
            }
            log.warn("{}.{} throw exception:{}", method.getDeclaringClass().getName(), method.getName(), exception);
            String msg = "未知错误";
            if (StringUtils.isNotBlank(exception.getMessage())) {
                msg = exception.getMessage();
            }
            ResultAmisVO resultAmisVO = new ResultAmisVO<>();
            resultAmisVO.setStatus(-1);
            resultAmisVO.setMsg(msg);
            resultAmisVO.setCode("500");
            log.warn("resultAmisVO = {}", JSON.toJSONString(resultAmisVO));

            return resultAmisVO;
        }
    }
}
