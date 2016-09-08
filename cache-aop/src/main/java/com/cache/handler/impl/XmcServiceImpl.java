package com.cache.handler.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

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
    
   // private static final Logger LOGGER = LoggerFactory.getLogger(XmcServiceImpl.class);
    
    private MemcachedClient client;
    
    private String hosts = "";
    
    private Integer poolSize = 1;
    
    private Integer connectTimeOut = 500;
    
    private Long optTimeOut = 3000L;
    
    private Long batchOptTimeOut = 5000L;
    
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
    public <T> T get(String key, long timeout, Class<T> clazz) throws CacheException {
        try {
            byte[] result =  client.get(key);
            if (result == null) {
                return null;
            }
            return cacheTranscoder.decode(result, clazz);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @Override
    public <T> Map<String, T> batchGet(List<String> keySet, Long timeOut, Class<T> clazz) throws CacheException {
        try {
            Map<String, byte[]> cacheResult = client.get(keySet, timeOut);
            if (cacheResult == null || cacheResult.size() <= 0) {
                return null;
            }
            Map<String, T> result = new HashMap<String, T>();
            for (Entry<String, byte[]> entry : cacheResult.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                result.put(entry.getKey(), cacheTranscoder.decode(entry.getValue(), clazz));
            }
            return result;
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @Override
    public <T> boolean set(String key, T value, int expireTime, long timeout) throws CacheException {
        try {
            if (value == null) {
                return false;
            }
            byte[] cacheValue = cacheTranscoder.encode(value);
            return client.set(key, expireTime, cacheValue, timeout);
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
    
    @PreDestroy
    public void shutdown() throws IOException {
        if (null != client) {
            client.shutdown();
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


    @Override
    public <T> T get(String key, long timeout, Type type) throws CacheException {
        try {
            byte[] result =  client.get(key);
            if (result == null) {
                return null;
            }
            return cacheTranscoder.decode(result, type);
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> batchGet(List<String> keySet, Long timeOut, Type clazz) throws CacheException {
        try {
            Map<String, byte[]> cacheResult = client.get(keySet, timeOut);
            if (cacheResult == null || cacheResult.size() <= 0) {
                return null;
            }
            Map<String, T> result = new HashMap<String, T>();
            for (Entry<String, byte[]> entry : cacheResult.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                result.put(entry.getKey(), (T) cacheTranscoder.decode(entry.getValue(), clazz));
            }
            return result;
        } catch (TimeoutException e) {
            throw new CacheException("mc timeout" + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new CacheException("mc interrupt" + e.getMessage(), e);
        } catch (MemcachedException e) {
            throw new CacheException("mc common error" + e.getMessage(), e);
        }
    }

    public void setOptTimeOut(Long optTimeOut) {
        this.optTimeOut = optTimeOut;
    }

    public void setBatchOptTimeOut(Long batchOptTimeOut) {
        this.batchOptTimeOut = batchOptTimeOut;
    }

    public Long getOptTimeOut() {
        return optTimeOut;
    }

    public Long getBatchOptTimeOut() {
        return batchOptTimeOut;
    }
    
}
