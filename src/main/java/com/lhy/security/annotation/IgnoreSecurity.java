package com.lhy.security.annotation;

import java.lang.annotation.*;

/**
 * @ClassName IgnoreSecurity
 * @Description
 * @Author lihengyu
 * @Date 2020/12/6 16:33
 * @Version 1.0
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSecurity {

}
