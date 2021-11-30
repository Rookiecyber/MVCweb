package cn.edu.cqu.anotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE ,ElementType.METHOD}) // 只能注解到类和方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    /**
     * 表示访问该方法的url
     * @return
     */
    String value() default "";
}
