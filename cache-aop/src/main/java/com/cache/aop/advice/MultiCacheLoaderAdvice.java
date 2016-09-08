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

import com.cache.aop.advice.common.MultiCacheAdvice;
import com.cache.aop.annotation.MultiCacheLoader;
import com.cache.aop.vo.CacheAnnotationData;
import com.cache.handler.CacheBasicService;

@Component
@Aspect
public class MultiCacheLoaderAdvice extends MultiCacheAdvice<MultiCacheLoader> {

    public MultiCacheLoaderAdvice() {
        super(MultiCacheLoader.class);
    }
    
    @Pointcut("@annotation(com.cache.aop.annotation.MultiCacheLoader)")
    public void loadCache() {
    }

    @Around("loadCache()")
    public Object cacheGetMulti(final ProceedingJoinPoint pjp) throws Throwable {
        CacheAnnotationData cacheAnnotationData = getAnnotationData(pjp); 
        CacheBasicService service = getCacheBaseService(cacheAnnotationData);
        Map<String, Object> keyMap = getCacheKey(cacheAnnotationData, pjp.getArgs());
        Map<String, Object> cacheResult = null;
        Map<Object, Object> mutilResult = null;
        List<String> keyList = new ArrayList<>(keyMap.keySet());
        if (cacheAnnotationData.getReturnType() == Map.class) {
            Type type = cacheAnnotationData.getInnerType()[1];
            cacheResult = service.batchGet(keyList, service.getOptTimeOut(), type);
        }
        
        List<Object> notExistList = new ArrayList<Object>();
        List<String> notExistKey = new ArrayList<String>();
        if (cacheResult != null) {
            mutilResult = new HashMap<Object, Object>();
            for (Entry<String, Object> entry : ((HashMap<String, Object>)cacheResult).entrySet()) {
                mutilResult.put(keyMap.get(entry.getKey()), entry.getValue());
            }
        }
        if (cacheResult != null && ((Map<?, ?>)cacheResult).size() == keyList.size()) {
            return mutilResult;
        } else {
            for (String key : keyList) {
                if (((Map<?, ?>)cacheResult).get(key) == null) {
                    notExistList.add(keyMap.get(key));
                    notExistKey.add(key);
                }
            }
            pjp.getArgs()[cacheAnnotationData.getCacheParamIndexList().get(0)] = notExistList;
        }
        Object result = pjp.proceed(pjp.getArgs());
        if (result == null && !cacheAnnotationData.isAllowNullValue()) {
            result = new Object();
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
