package cn.edu.cqu.anotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 只能注解到类
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    /**
     * controller注册别名
     * @return
     */
    String value() default "";
}
