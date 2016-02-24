package com.cache.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.vo.CacheAnnotationData;

@Component
@Aspect
public class CacheLoaderAdvice extends SingleCacheAdvice<CacheLoader> {
    
    public CacheLoaderAdvice() {
        super(CacheLoader.class);
    }

    @Pointcut("@annotation(com.cache.aop.annotation.CacheLoader)")
    public void loadCache() {
    }

    @Around("loadCache()")
    public Object cacheGetSingle(final ProceedingJoinPoint pjp) throws Throwable {
        CacheAnnotationData cacheAnnotationData = getAnnotationData(pjp); 
        CacheBaseService service = getCacheBaseService(cacheAnnotationData);
        String key = getCacheKey(cacheAnnotationData, pjp.getArgs());
        Object result = null;
        if (!cacheAnnotationData.isReload()) {
            result = service.get(key, cacheAnnotationData.getReturnType());
            if (result != null) {
                return result;
            }
        }
        result = pjp.proceed();
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
        }
        service.set(key, result, cacheAnnotationData.getTimeout());
        return result;
    }
    
}
