package com.cache.handler.impl;

import com.alibaba.fastjson.JSON;
import com.cache.handler.CacheTranscoder;

/**
 * 
 * json缓存编码解码器
 * @author yangyang.xu
 *
 */
public class JsonCacheTranscoder implements CacheTranscoder {

    @Override
    public <T> T decode(byte[] obj, Class<T> clasz) {
        if (obj == null || obj.length <= 0) {
            return null;
        }
        return JSON.parseObject(new String(obj), clasz);
    }

    @Override
    public <T> byte[] encode(T obj) {
        return null;
    }

}
