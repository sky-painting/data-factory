package com.coderman.tianhua.datafactory.core.function;

import java.lang.annotation.*;

/**
 * @description: DataSourceFunction <br>
 * @date: 2020/12/31 23:27 <br>
 * @author: coderman <br>
 * @version: 1.0 <br>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceFunctionMethod {

    /**
     * 内置的数据源code
     * @return
     */
    String dataSourceCode() default "";
}
