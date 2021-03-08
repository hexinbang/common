package com.hxb.common.generator;

import java.lang.annotation.*;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/10/10 20:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface TableName {
    String value() default "";
}
