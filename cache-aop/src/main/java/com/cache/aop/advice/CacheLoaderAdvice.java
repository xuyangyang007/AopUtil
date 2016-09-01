package com.cache.aop.advice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        boolean isMulti = false;
        if (keyList != null && keyList.size() == 1) {
            result = service.get(keyList.get(0), service.getOptTimeOut(), cacheAnnotationData.getGenType());
        }
        if (keyList != null && keyList.size() > 1) {
            if (cacheAnnotationData.getReturnType() == Map.class) {
                Type type = cacheAnnotationData.getInnerType()[1];
                result = service.batchGet(keyList, service.getOptTimeOut(), type);
                isMulti = true;
            }
        }
        if (!isMulti && result != null) {
            return result;
        }
        
        if (isMulti) {
            if (((Map)result).size() == keyList.size()) {
                return result;
            } else {
                List<String> notExistList = new ArrayList<String>();
                for (String key : keyList) {
                    if (((Map)result).get(key) == null) {
                        notExistList.add(key);
                    }
                }
                pjp.getArgs()[cacheAnnotationData.getCacheParamIndexList().get(0)] = notExistList;
            }
        }
        result = pjp.proceed(pjp.getArgs());
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
        }
        if (isMulti) {
            for (String key : keyList) {
                
            }
        } else {
            service.set(keyList.get(0), result, cacheAnnotationData.getTimeout(), service.getOptTimeOut());
        }
        return result;
    }
    
}
