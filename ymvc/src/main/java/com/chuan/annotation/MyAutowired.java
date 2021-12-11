package com.chuan.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD}) // 构造器。方法，成员变量
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
    String value() default "";
}
