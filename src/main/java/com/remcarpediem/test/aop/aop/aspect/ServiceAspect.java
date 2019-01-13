package com.remcarpediem.test.aop.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ServiceAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class.getName());

    @Pointcut("execution(* com.remcarpediem.test.aop.service..*(..))")
    public void aspect() {}

    @Before("aspect()")
    public void log(JoinPoint joinPoint) {
        if (LOGGER.isInfoEnabled()) {
            Signature signature = joinPoint.getSignature();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            String[] argumentNames = methodSignature.getParameterNames();

            StringBuilder sb = new StringBuilder(className + "." + methodName + "(");

            for (int i = 0; i< arguments.length; i++) {
                Object argument = arguments[i];
                sb.append(argumentNames[i] + "->");
                sb.append(argument != null ? argument.toString() : "null ");
            }
            sb.append(")");

            LOGGER.info(sb.toString());
        }
    }

    /** 需要注意，around函数必须要有返回值，否则织入的函数的返回值都是null **/
    @Around("aspect()")
    public Object around(JoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        try {
            Object ret = ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            if(LOGGER.isInfoEnabled()){
                LOGGER.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
            return ret;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if(LOGGER.isInfoEnabled()){
                LOGGER.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
            throw e;
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning(value = "execution(* com.remcarpediem.test.aop.service..*(..))", returning = "evt")
    public void afterReturn(JoinPoint joinPoint, Object evt){
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("afterReturn " + joinPoint + (evt != null ? evt.toString() : "null or void"));
        }
    }


    /** 配置抛出异常后通知,使用在方法aspect()上注册的切入点 需要注意Around时别把Exception捕获啦**/

    @AfterThrowing(pointcut="aspect()", throwing="ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex) throws Exception{
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
        }
        /** 这里可以做层之间的Exception的转换, 比如Dao层的exception可以统一为DataAccessException等**/
//        throw new ServiceException(ex.getMessage());
    }



}
