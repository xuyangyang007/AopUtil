package com.cache.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;

import com.cache.aop.vo.CacheAnnotationData;

public abstract class SingleCacheAdvice <T extends Annotation> extends CommonAdvice<CacheAnnotationData> {
    
    private final Class<T> annotationClass;

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

    public String getCacheKey(final CacheAnnotationData data, final Object[] args) throws Exception {
        String key = "";
        key += data.getCacheNameSpace() + 
                ":" + data.getCacheKeyPrefix() +
                ":" + data.getCacheKeySuffix();
        // TODO
        for (Integer index : data.getCacheParamIndexList()) {
         // TODO   String json = FastJsonUtil.bean2Json(args[index]);
            String json = "";
            key += ":" +json;
        }
        if (key.length() > 100) {
           // key = MD5Util.MD5(key); 
        }
        return key;
    }
    
    
    public CacheBaseService getCacheBaseService(CacheAnnotationData data) {
        if (data.getCacheKeyPrefix().equals("mc")) {
            //McFactoryService service = (McFactoryService) SpringUtil.getBean("mcFactoryService");
            //return service;
        }
        //RedisFactoryService service = (RedisFactoryService) SpringUtil.getBean("redisFactoryService");
        return null;
    }

    
}
