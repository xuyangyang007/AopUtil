package com.cache.aop.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cache.aop.advice.common.SingleCacheAdvice;
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
        Map<String, Object> keyMap = getCacheKey(cacheAnnotationData, pjp.getArgs());
        Object cacheResult = null;
        List<String> keyList = new ArrayList<>(keyMap.keySet());
        if (keyList != null && keyList.size() >= 1) {
            cacheResult = service.get(keyList.get(0), service.getOptTimeOut(), cacheAnnotationData.getGenType());
        }
        if (cacheResult != null) {
            return cacheResult;
        }
        List<String> notExistKey = new ArrayList<String>();
        notExistKey.add(keyList.get(0));
        Object result = pjp.proceed(pjp.getArgs());
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
        }
        service.set(notExistKey.get(0), result, cacheAnnotationData.getTimeout(), service.getOptTimeOut());
        return result;
    }
    
}
