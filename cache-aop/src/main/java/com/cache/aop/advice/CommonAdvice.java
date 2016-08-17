package com.cache.aop.advice;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 公共的注解处理服务
 * @since 1.7
 * @author yangyang.xu
 *
 */
public abstract class CommonAdvice<T> {

    private static final Logger logger = LoggerFactory.getLogger(CommonAdvice.class);
    
    protected Logger getLogger() {
        return logger;
    }
    
    public Method getMethod(final JoinPoint jp) throws Exception {
        if (jp == null) {
            throw new Exception("JoinPoint is null");
        }
        final Signature sig = jp.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new NoSuchMethodException("NoSuchMethodException");
        }
        MethodSignature methodSignature = (MethodSignature) sig;  
        Method method = methodSignature.getMethod();  
        return method;
    }
    
    public Class getTargetClass(final JoinPoint jp) throws Exception {
        if (jp == null) {
            throw new Exception("JoinPoint is null");
        }
        return jp.getTarget().getClass();
    }
    
    protected abstract T getAnnotationData(ProceedingJoinPoint pjp);
    
}
