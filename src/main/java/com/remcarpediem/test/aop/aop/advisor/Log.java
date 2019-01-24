package com.remcarpediem.test.aop.aop.advisor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {
    LogLevel value();

    boolean isTest() default false;
}
