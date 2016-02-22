package com.cache.aop.advice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.annotation.CacheNamespace;
import com.cache.aop.annotation.CacheParam;
import com.cache.aop.vo.CacheAnnotationData;


public class CacheAnnotationDataBuilder {
    
    public static CacheAnnotationData buildAnnotationData(final Annotation annotation,
            final Class<? extends Annotation> expectedAnnotationClass, final Method targetMethod, final Class<?> tagetClass) {
        final CacheAnnotationData data = new CacheAnnotationData();

        try {
            populateClassCacheName(data, tagetClass);
            populateCacheName(data, targetMethod);
            populateCacheArgs(data, targetMethod);
        } catch (Exception ex) {
            throw new RuntimeException("Problem assembling Annotation information.", ex);
        }

        return data;
    }
    
    private static void populateCacheArgs(CacheAnnotationData data, Method targetMethod) {
        Annotation[][] paramAnotations = targetMethod.getParameterAnnotations();
        if (paramAnotations == null) {
            return ;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < paramAnotations.length; i++) {
            for (Annotation annotation : paramAnotations[i]) {
                if (annotation.annotationType().equals(CacheParam.class)) {
                    list.add(i);
                }
            }
        }
        data.setCacheParamIndexList(list);
    }

    private static void populateClassCacheName(CacheAnnotationData data, Class<?> tagetClass) {
        CacheNamespace cacheKeyConfig = (CacheNamespace) tagetClass.getAnnotation(CacheNamespace.class);
        if (cacheKeyConfig == null) {
            return;
        }
        data.setCacheNameSpace(cacheKeyConfig.nameSpace());
    }

    static void populateCacheName(final CacheAnnotationData data, final Method targetMethod) {
        CacheLoader cache = targetMethod.getAnnotation(CacheLoader.class);
        if (cache == null) {
            return;
        }

        data.setCacheKeyPrefix(cache.cacheKeyPrefix());
        data.setCacheKeySuffix(cache.cacheKeySuffix());
        data.setCacheNode(cache.cacheNode());
        data.setReload(cache.isReload());
        data.setTimeout(cache.timeout());
    }

}
