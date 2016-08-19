package com.cache.handler;

/**
 * 缓存编码解码
 * @author yangyang.xu
 *
 */
public interface CacheTranscoder {
    
    <T> T decode(byte[] obj, Class<?> clasz);
    
    <T> byte[] encode(T obj);

}
