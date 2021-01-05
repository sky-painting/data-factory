package com.coderman.tianhua.datafactory.core.function;

import java.lang.annotation.*;

/**
 * @description: DataSourceFunction 该注解将函数实现与数据源code进行关联
 * @date: 2020/12/31 23:27 <br>
 * @author: coderman <br>
 * @version: 1.0 <br>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataSourceFunction {

    /**
     * 内置的数据源code
     * @return
     */
    String dataSourceCode() default "";
}
