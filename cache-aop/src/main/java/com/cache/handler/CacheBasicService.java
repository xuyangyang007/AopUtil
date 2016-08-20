package com.cache.handler;

import java.util.List;
import java.util.Map;

import com.cache.exception.CacheException;


/**
 * 缓存基础服务
 * @author yangyang.xu
 *
 */

public interface CacheBasicService {
    
    public byte[] get(String key, long timeout) throws CacheException;
    
    public Map<String, byte[]> batchGet(List<String> keySet, Long timeOut) throws CacheException;
    
    public boolean set(String key, byte[] value, int expireTime, long timeout) throws CacheException;
    
    public boolean delete(String key, long timeout) throws CacheException;

}
