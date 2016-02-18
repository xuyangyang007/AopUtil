package com.cache.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * load data into cache
 * 
 * @since 1.7
 * @author yangyang.xu
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheLoader {
    
    public String cacheKeyPrefix() default "";
    
    public String cacheKeySuffix() default "";
    
    public int timeout() default 300;
    
    public boolean allowNullValue() default false;
    
    public String cacheNode() default "redis";
    
    public boolean isReload() default false;
    
    public String reloadInterval() default "";

}
