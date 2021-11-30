package cn.edu.cqu.anotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE ,ElementType.METHOD}) // ֻ��ע�⵽��ͷ�����
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    /**
     * ��ʾ���ʸ÷�����url
     * @return
     */
    String value() default "";
}
