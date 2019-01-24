/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2019 All Rights Reserved.
 */
package com.remcarpediem.test.aop.aop.proxy;

import com.remcarpediem.test.aop.aop.aspect.ServiceAspect;
import com.sun.corba.se.spi.activation.ServerManager;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 *
 * @author libing
 * @version $Id: ProxyConfig.java, v 0.1 2019年01月23日 下午7:39 zt Exp $
 */
@Configuration
public class ProxyConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProxyConfig.class.getName());

    @Bean
    public ServiceManager serviceManager() {
        ServiceManager serviceManager = new ServiceManager();
        ProxyFactory proxyFactory = new ProxyFactory(serviceManager);
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                LOGGER.info("执行目标方法调用之前的逻辑");
            }
        });
        proxyFactory.addAdvisor(new MyAdvisor());
        return (ServiceManager) proxyFactory.getProxy();
    }




}

 class MyAdvisor implements PointcutAdvisor {

    @Override
    public Advice getAdvice() {
        return new MethodBeforeAdvice() {

            @Override
            public void before(Method method, Object[] args,

                               Object target) throws Throwable {


                System.out.println("BeforeAdvice实现，在目标方法被调用前调用，目标方法是："

                        + method.getDeclaringClass().getName() + "."
                        + method.getName());
            }
        };
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @Override
    public Pointcut getPointcut() {
        //匹配所有的方法调用
        return Pointcut.TRUE;
    }

}
