package com.cache.aop.advice.builder;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.vo.CacheAnnotationData;

@Component
public class SingleAnnotationBuilder extends CacheAnnotationDataBuilder {

    @Override
    protected void populateCacheName(CacheAnnotationData data, Method targetMethod) {
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
