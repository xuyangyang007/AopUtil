package com.cache.demo;

import java.util.List;
import java.util.Map;

import com.cache.demo.vo.CacheData;


public interface CacheDemo {
    
    public void deleteCacheById(Long id);
    
    public Object getCacheById(Long id);
    
    public Object updateCache(Long id);
    
    public List<CacheData> getCacheData(Integer id);
    
    public Map<Integer, CacheData> batchGetData(List<Integer> idList);

}
