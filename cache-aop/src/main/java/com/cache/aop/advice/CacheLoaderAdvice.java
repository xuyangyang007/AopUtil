package com.cache.aop.advice;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        Map<Object, Object> mutilResult = null;
        boolean isMulti = false;
        List<String> keyList = new ArrayList<>(keyMap.keySet());
        if (keyList != null && keyList.size() == 1) {
            cacheResult = service.get(keyList.get(0), service.getOptTimeOut(), cacheAnnotationData.getGenType());
        }
        if (keyList != null && keyList.size() > 1) {
            if (cacheAnnotationData.getReturnType() == Map.class) {
                Type type = cacheAnnotationData.getInnerType()[1];
                cacheResult = service.batchGet(keyList, service.getOptTimeOut(), type);
                isMulti = true;
            }
        }
        if (!isMulti && cacheResult != null) {
            return cacheResult;
        }
        
        List<Object> notExistList = new ArrayList<Object>();
        List<String> notExistKey = new ArrayList<String>();
        if (isMulti) {
            if (cacheResult == null) {
                cacheResult = new HashMap<String, Object>();
            }
            mutilResult = new HashMap<Object, Object>();
            for (Entry<String, Object> entry : ((HashMap<String, Object>)cacheResult).entrySet()) {
                mutilResult.put(keyMap.get(entry.getKey()), entry.getValue());
            }
            if (((Map<?, ?>)cacheResult).size() == keyList.size()) {
                return cacheResult;
            } else {
                for (String key : keyList) {
                    if (((Map<?, ?>)cacheResult).get(key) == null) {
                        notExistList.add(keyMap.get(key));
                        notExistKey.add(key);
                    }
                }
                pjp.getArgs()[cacheAnnotationData.getCacheParamIndexList().get(0)] = notExistList;
            }
        } else {
            notExistKey.add(keyList.get(0));
        }
        Object result = pjp.proceed(pjp.getArgs());
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
        }
        if (!isMulti) {
            service.set(notExistKey.get(0), result, cacheAnnotationData.getTimeout(), service.getOptTimeOut());
            return result;
        }
        if (mutilResult != null) {
            ((Map<Object, Object> ) result).putAll(mutilResult);
        }
        for (String key : notExistKey) {
            service.set(key, ((Map<?, ?>)result).get(key), cacheAnnotationData.getTimeout(), service.getOptTimeOut());
        }
        return result;
    }
    
}
