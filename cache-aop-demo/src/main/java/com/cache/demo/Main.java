package com.cache.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cache.demo.impl.CacheDemoImpl;

public class Main {

    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(
                "src/main/resources/applicationContext-tx-test.xml");
        CacheDemoImpl demo = (CacheDemoImpl) factory.getBean("cacheDemoImpl");
        demo.getCacheById(1L);
    }

}
