package com.cache.demo.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cache.aop.annotation.CacheCleaner;
import com.cache.aop.annotation.CacheLoader;
import com.cache.aop.annotation.CacheNamespace;
import com.cache.aop.annotation.CacheParam;
import com.cache.demo.CacheDemo;
import com.cache.demo.vo.CacheData;

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

    @Override
    @CacheLoader(cacheKeyPrefix="detailList", timeout=3000)
    public List<CacheData> getCacheData(Integer id) {
        CacheData data1 = new CacheData();
        data1.setDescription("11");
        data1.setId(1);
        data1.setName("11");
        CacheData data2 = new CacheData();
        data2.setDescription("22");
        data2.setId(2);
        data2.setName("22");
        List<CacheData> list = Arrays.asList(data1, data2);
        return list;
    }

}
