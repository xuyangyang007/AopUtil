package com.cache.demo.impl;

import org.springframework.stereotype.Service;

import com.cache.aop.annotation.CacheCleaner;
import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.annotation.CacheNamespace;
import com.cache.aop.annotation.CacheParam;
import com.cache.demo.CacheDemo;

/**
 * 
 * cache-aop的demo
 * @author yangyang.xu
 *
 */
@CacheNamespace(nameSpace="cache-demo")
@Service
public class CacheDemoImpl implements CacheDemo {
    
    @CacheCleaner(cacheKeyPrefix="detail")
    public void deleteCacheById(@CacheParam Long id) {
        
    }
    
    @CacheLoader(cacheKeyPrefix="detail", timeout=3000)
    public Object getCacheById(@CacheParam Long id) {
        return new Object();
    }
    
    @CacheLoader(cacheKeyPrefix="detail", timeout=3000)
    public Object updateCache(@CacheParam Long id) {
        return new Object();
    }

}
