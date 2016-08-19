package com.cache.handler.impl;

import com.cache.handler.CacheTranscoder;

/**
 * 
 * json缓存编码解码器
 * @author yangyang.xu
 *
 */
public class JsonCacheTranscoder implements CacheTranscoder {

    @Override
    public <T> T decode(byte[] obj, Class<?> clasz) {
        return null;
    }

    @Override
    public <T> byte[] encode(T obj) {
        return null;
    }

}
