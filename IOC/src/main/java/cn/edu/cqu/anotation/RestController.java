package cn.edu.cqu.anotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // ֻ��ע�⵽��
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    /**
     * controllerע�����
     * @return
     */
    String value() default "";
}
