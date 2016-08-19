package com.cache.handler.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cache.handler.CacheBasicService;

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
    public byte[] get(String key, long timeout) {
        return null;
    }

    @Override
    public Map<String, byte[]> batchGet(List<String> keySet, Long timeOut) {
        return null;
    }

    @Override
    public boolean set(String key, byte[] value, int expireTime, long timeout) {
        return false;
    }

    @Override
    public boolean delete(String key, long timeout) {
        return false;
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
