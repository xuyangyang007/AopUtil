package com.cache.handler.impl;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cache.handler.CacheTranscoder;

/**
 * 
 * json缓存编码解码器
 * @author yangyang.xu
 *
 */
@Component
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
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj).getBytes();
    }

}
