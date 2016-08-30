package com.cache.aop.advice;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.vo.CacheAnnotationData;
import com.cache.handler.CacheBasicService;

/**
 * 缓存加载器注解的处理
 * @author yangyang.xu
 *
 */
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
        CacheBasicService service = getCacheBaseService(cacheAnnotationData);
        List<String> keyList = getCacheKey(cacheAnnotationData, pjp.getArgs());
        Object result = null;
        if (keyList != null && keyList.size() == 1) {
            result = service.get(keyList.get(0), service.getOptTimeOut(), cacheAnnotationData.getGenType());
        }
        if (keyList != null && keyList.size() > 1) {
                
        }
        if (result != null) {
            return result;
        }
        result = pjp.proceed();
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
        }
        service.set("xx", result, cacheAnnotationData.getTimeout(), service.getOptTimeOut());
        return result;
    }
    
}
