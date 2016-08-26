package com.cache.handler;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.cache.exception.CacheException;


/**
 * 缓存基础服务
 * @author yangyang.xu
 *
 */

public interface CacheBasicService {
    
    public <T> T get(String key, long timeout, Class<T> clazz) throws CacheException;
    
    public <T> T get(String key, long timeout, Type type) throws CacheException;
    
    public <T> Map<String, T> batchGet(List<String> keySet, Long timeOut, Class<T> clazz) throws CacheException;
    
    public <T> boolean set(String key, T value, int expireTime, long timeout) throws CacheException;
    
    public boolean delete(String key, long timeout) throws CacheException;
    
    public Integer getOptTimeOut();

    public Integer getBatchOptTimeOut();

}
