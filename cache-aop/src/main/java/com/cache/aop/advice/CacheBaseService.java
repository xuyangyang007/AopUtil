package com.cache.aop.advice;

/**
 * 缓存的基础服务
 * @author yangyang.xu
 *
 */
public interface CacheBaseService {

    public String get(String key);
    
    public void delete(String key);
    
    public boolean set(String key, Object obj, Integer liveTime);
    
    public <T> T get(String key, Class<T> clazz);
    
}
