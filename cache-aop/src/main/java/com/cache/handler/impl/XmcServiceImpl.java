package com.cache.handler.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.exception.CacheException;
import com.cache.handler.CacheBasicService;
import com.cache.handler.CacheTranscoder;

/**
 * xmemcache缓存实现
 * @author yangyang.xu
 *
 */
@Service
public class XmcServiceImpl implements CacheBasicService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(XmcServiceImpl.class);
    
    private MemcachedClient client;
    
    private String hosts = "";
    
    private Integer poolSize = 1;
    
    private Integer connectTimeOut = 500;
    
    @Autowired
    private CacheTranscoder cacheTranscoder;
    
    @PostConstruct
    public void initMcClient() throws IOException {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(hosts));
        builder.setFailureMode(false);
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setConnectionPoolSize(poolSize);
        builder.setConnectTimeout(connectTimeOut);
        client = builder.build();
    }

    @Override
    public byte[] get(String key, long timeout) throws CacheException {
        try {
            return client.get(key);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, byte[]> batchGet(List<String> keySet, Long timeOut) throws CacheException {
        try {
            return client.get(keySet, timeOut);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @Override
    public boolean set(String key, byte[] value, int expireTime, long timeout) throws CacheException {
        try {
            return client.set(key, expireTime, value, timeout);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(String key, long timeout) throws CacheException {
        try {
            return client.delete(key, timeout);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

}
