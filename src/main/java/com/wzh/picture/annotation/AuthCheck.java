package com.wzh.picture.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)   // 注解生效范围->method
@Retention(RetentionPolicy.RUNTIME) // 注解生效的时间
public @interface AuthCheck {

    /**
     * 用户必须具有的角色
     * @return 角色
     */
    String mustRole() default "";
}
