package com.cache.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.cache.aop.vo.CacheAnnotationData;
import com.cache.handler.CacheBasicService;

/**
 * 单个key的处理
 * @author yangyang.xu
 *
 * @param <T>
 */
@Component
public abstract class SingleCacheAdvice <T extends Annotation> extends CommonAdvice<CacheAnnotationData> {
    
    private final Class<T> annotationClass;
    
    @Autowired
    private CacheBasicService xmcServiceImpl;

    public SingleCacheAdvice(final Class<T> annotationClass) {
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

    public List<String> getCacheKey(final CacheAnnotationData data, final Object[] args) throws Exception {
        List<String> keyList = new ArrayList<String>();
        String keyPrefix = data.getCacheNameSpace() + 
                ":" + data.getCacheKeyPrefix() +
                ":" + data.getCacheKeySuffix();
        for (Integer index : data.getCacheParamIndexList()) {
            if (args[index] == null) {
                continue;
            }
            if (args[index] instanceof Collection<?>) {
                for (Object obj : (Collection<?>)args[index]) {
                    keyList.add(keyPrefix + ":" + obj.toString());
                }
            } else {
                keyList.add(keyPrefix + ":" + args[index].toString());
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
