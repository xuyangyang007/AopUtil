package com.cache.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * clean the cache
 * 
 * @since 1.7
 * @author yangyang.xu
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheCleaner {
    
    public String cacheKeyPrefix() default "";
    
    public String cacheKeySuffix() default "";
}
