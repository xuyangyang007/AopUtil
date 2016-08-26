package com.cache.aop.vo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * cache annotaionData
 * 
 * @author yangyang.xu
 *
 */
public class CacheAnnotationData {
    
    private String cacheNameSpace;
    
    private String cacheKeyPrefix;
    
    private String cacheKeySuffix;
    
    private int timeout;
    
    private boolean allowNullValue;
    
    private String cacheNode;
    
    private boolean isReload;
    
    private List<Integer> cacheParamIndexList;
    
    private Class<?> returnType;
    
    private Type genType;
    
    public Type getGenType() {
        return genType;
    }

    public void setGenType(Type genType) {
        this.genType = genType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public List<Integer> getCacheParamIndexList() {
        return cacheParamIndexList;
    }

    public void setCacheParamIndexList(List<Integer> cacheParamIndexList) {
        this.cacheParamIndexList = cacheParamIndexList;
    }

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }

    public void setCacheKeyPrefix(String cacheKeyPrefix) {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }

    public String getCacheKeySuffix() {
        return cacheKeySuffix;
    }

    public void setCacheKeySuffix(String cacheKeySuffix) {
        this.cacheKeySuffix = cacheKeySuffix;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isAllowNullValue() {
        return allowNullValue;
    }

    public void setAllowNullValue(boolean allowNullValue) {
        this.allowNullValue = allowNullValue;
    }

    public String getCacheNode() {
        return cacheNode;
    }

    public void setCacheNode(String cacheNode) {
        this.cacheNode = cacheNode;
    }

    public boolean isReload() {
        return isReload;
    }

    public void setReload(boolean isReload) {
        this.isReload = isReload;
    }

    public String getCacheNameSpace() {
        return cacheNameSpace;
    }

    public void setCacheNameSpace(String cacheNameSpace) {
        this.cacheNameSpace = cacheNameSpace;
    }

}
