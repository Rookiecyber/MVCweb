package com.chuan.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE}) // 类
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
    String value() default "";
}
