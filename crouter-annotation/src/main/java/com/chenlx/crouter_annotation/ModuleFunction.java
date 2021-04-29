package com.chenlx.crouter_annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenlx
 * <p>
 * 注解用于扫描代码逻辑，添加注解后才能被实例化
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleFunction {

    String name() default "";

    Class[] interceptor() default void.class;


}
