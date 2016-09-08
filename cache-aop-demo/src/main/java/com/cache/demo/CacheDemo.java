package com.cache.demo;

import java.util.List;
import java.util.Map;

import com.cache.demo.vo.CacheData;
import com.cache.demo.vo.CacheKey;


public interface CacheDemo {
    
    public void deleteCacheById(Long id);
    
    public CacheData getCacheById(CacheKey id);
    
    public Object updateCache(Long id);
    
    public List<CacheData> getCacheData(Integer id);
    
    public Map<Integer, CacheData> batchGetData(List<Integer> idList);

}
