package com.cache.aop.advice.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.cache.aop.annotation.CacheNamespace;
import com.cache.aop.annotation.CacheParam;
import com.cache.aop.vo.CacheAnnotationData;


/**
 * 缓存注解构建器
 * @author yangyang.xu
 *
 */
public abstract class CacheAnnotationDataBuilder {
    
    public CacheAnnotationData buildAnnotationData(final Annotation annotation,
            final Class<? extends Annotation> expectedAnnotationClass, final Method targetMethod, final Class<?> tagetClass) {
        final CacheAnnotationData data = new CacheAnnotationData();

        try {
            populateClassCacheName(data, tagetClass);
            populateCacheName(data, targetMethod);
            populateCacheArgs(data, targetMethod);
            data.setReturnType(targetMethod.getReturnType());
            data.setGenType(targetMethod.getGenericReturnType());
            if(targetMethod.getGenericReturnType() instanceof ParameterizedType){ 
                ParameterizedType parameterizedType= (ParameterizedType) targetMethod.getGenericReturnType(); 
                Type[] types = parameterizedType.getActualTypeArguments();
                data.setInnerType(types);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Problem assembling Annotation information.", ex);
        }

        return data;
    }
    
    private void populateCacheArgs(CacheAnnotationData data, Method targetMethod) {
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
    
    public void setCacheArgs(Object param, Method targetMethod) {
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
    }

    private void populateClassCacheName(CacheAnnotationData data, Class<?> tagetClass) {
        CacheNamespace cacheKeyConfig = (CacheNamespace) tagetClass.getAnnotation(CacheNamespace.class);
        if (cacheKeyConfig == null) {
            return;
        }
        data.setCacheNameSpace(cacheKeyConfig.nameSpace());
    }

    protected abstract void populateCacheName(final CacheAnnotationData data, final Method targetMethod);

}
