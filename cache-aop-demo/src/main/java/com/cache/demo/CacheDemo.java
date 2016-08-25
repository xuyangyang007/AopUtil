package com.cache.demo;


public interface CacheDemo {
    
    public void deleteCacheById(Long id);
    
    public Object getCacheById(Long id);
    
    public Object updateCache(Long id);

}
