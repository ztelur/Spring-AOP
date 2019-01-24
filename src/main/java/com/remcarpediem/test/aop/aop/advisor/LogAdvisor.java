package com.remcarpediem.test.aop.aop.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LogAdvisor extends AbstractPointcutAdvisor {
    private Advice advice;
    private Pointcut pointcut;

    @PostConstruct
    public void init() {
        this.pointcut = new AnnotationMatchingPointcut((Class) null, Log.class);
        this.advice = new LogMethodInterceptor();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
