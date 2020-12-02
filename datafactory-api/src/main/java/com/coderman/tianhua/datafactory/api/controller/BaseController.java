package com.coderman.tianhua.datafactory.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Description:控制层基础父类
* @Author:fanchunshuai
* @CreateTime:2020-12-02 23:07:13
* @version v1.0
*/
public class BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 从request中获取指定的字符串
     * @param parameterName
     * @return
     */
    protected String getParameterString(String parameterName) {
        //return RequestUtil.getParameterString(parameterName);
        return "";
    }

    /**
     * 从request中获取指定的整型
     * @param parameterName
     * @return
     */
    protected Integer getParameterInteger(String parameterName) {
       // return RequestUtil.getParameterInteger(parameterName);
       return 0;
    }

    /**
     * 从request中获取指定的长整型
     * @param parameterName
     * @return
     */
    protected Long getParameterLong(String parameterName) {
        //return RequestUtil.getParameterLong(parameterName);
        return 0L;
    }
}
