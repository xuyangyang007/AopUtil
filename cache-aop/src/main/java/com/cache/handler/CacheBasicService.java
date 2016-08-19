package com.cache.handler;

import java.util.List;
import java.util.Map;


/**
 * 缓存基础服务
 * @author yangyang.xu
 *
 */

public interface CacheBasicService {
    
    public byte[] get(String key, long timeout);
    
    public Map<String, byte[]> batchGet(List<String> keySet, Long timeOut);
    
    public boolean set(String key, byte[] value, int expireTime, long timeout);
    
    public boolean delete(String key, long timeout);

}
