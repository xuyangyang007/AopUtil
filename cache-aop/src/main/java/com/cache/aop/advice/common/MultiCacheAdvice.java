package com.cache.aop.advice.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cache.aop.advice.CacheAnnotationDataBuilder;
import com.cache.aop.vo.CacheAnnotationData;
import com.cache.exception.CacheException;
import com.cache.handler.CacheBasicService;

@Component
public abstract class MultiCacheAdvice<T extends Annotation> extends CommonAdvice<CacheAnnotationData> {
    
    
    private final Class<T> annotationClass;
    
    @Autowired
    private CacheBasicService xmcServiceImpl;

    public MultiCacheAdvice(final Class<T> annotationClass) {
        this.annotationClass = annotationClass;
    }
    
    @Override
    protected CacheAnnotationData getAnnotationData(final ProceedingJoinPoint pjp) {
        final T annotation;
        CacheAnnotationData data = null;
        try {
            final Method methodToCache = getMethod(pjp);
            annotation = methodToCache.getAnnotation(annotationClass);
            if (annotation == null) {
                return null;
            }
            data = CacheAnnotationDataBuilder.buildAnnotationData(annotation, annotationClass, methodToCache, pjp.getTarget().getClass());

        } catch (Throwable ex) {
            getLogger()
                    .warn(String.format("Caching on method %s aborted due to an error.", pjp.toShortString()), ex);
        }
        return data;
    }

    public Map<String, Object> getCacheKey(final CacheAnnotationData data, final Object[] args) throws Exception {
        Map<String, Object> keyList = new HashMap<String, Object>();
        String keyPrefix = data.getCacheNameSpace() + 
                ":" + data.getCacheKeyPrefix() +
                ":" + data.getCacheKeySuffix();
        for (Integer index : data.getCacheParamIndexList()) {
            if (args[index] == null) {
                continue;
            }
            if (args[index] instanceof Collection<?>) {
                for (Object obj : (Collection<?>)args[index]) {
                    keyList.put(keyPrefix + ":" + obj.toString(), obj);
                }
            } else {
                throw new CacheException("参数必须是列表");
            }
            break;
        }
        return keyList;
    }
    
    
    public CacheBasicService getCacheBaseService(CacheAnnotationData data) {
        if (data.getCacheNode().equals("mc")) {
            return xmcServiceImpl;
        }
        return null;
    }

}
