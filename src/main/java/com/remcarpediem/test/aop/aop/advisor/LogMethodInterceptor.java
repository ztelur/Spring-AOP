package com.remcarpediem.test.aop.aop.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class LogMethodInterceptor implements MethodInterceptor {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Log log = method.getAnnotation(Log.class);
        LogLevel level = log.value();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();

        StringBuilder sb = (new StringBuilder()).append(className + "." + methodName);

        if (log.isTest()) {
            /** 这里无法获取到函数的参数名称，但是Apsect是可以获取到的 **/
            sb.append(" and arguments are ");
            Object[] arguments = methodInvocation.getArguments();
            for (Object o : arguments) {
                /** 处理传参为null的情况**/
                if (o != null) {
                    sb.append(" " + o.toString());
                } else {
                    sb.append("null");
                }
            }
        }
        if (LogLevel.INFO.equals(level)) {
            LOGGER.info(sb.toString());
        } else if (LogLevel.WARN.equals(level)) {
            LOGGER.warn(sb.toString());
        } else if (LogLevel.ERROR.equals(level)) {
            LOGGER.error(sb.toString());
        }
        return methodInvocation.proceed();
    }
}
